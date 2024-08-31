package ru.koryruno.MetricsProducerMicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.koryruno.MetricsProducerMicroservice.exception.MetricSendException;
import ru.koryruno.MetricsProducerMicroservice.model.MetricProducerDto;
import ru.koryruno.MetricsProducerMicroservice.model.MetricProducerEvent;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class MetricStatService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, MetricProducerEvent> kafkaTemplate;

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

//    private final MetricProducerServiceImpl metricsProducerService;

//    @Scheduled(fixedRate = 10000)
    public void collectAndSendMetrics() {
        log.info("Scheduled task is running");

        try {
            ResponseEntity<Map> metricsResponse = restTemplate.getForEntity("http://localhost:9091/actuator/metrics", Map.class);
            Map<String, Object> metricsMap = metricsResponse.getBody();

            if (metricsMap == null || !metricsMap.containsKey("names")) {
                log.warn("No metric names found.");
                return;
            }

            List<String> metricNames = (List<String>) metricsMap.get("names");
            metricNames.forEach(this::collectAndSendMetric);

        } catch (Exception e) {
            log.error("Failed to collect and send metrics: {}", e.getMessage());
        }
    }

    private void collectAndSendMetric(String metricName) {
        try {
            ResponseEntity<Map> metricDetailResponse = restTemplate.getForEntity("http://localhost:9091/actuator/metrics/" + metricName, Map.class);
            Map<String, Object> metricDetailMap = metricDetailResponse.getBody();

            if (metricDetailMap == null || !metricDetailMap.containsKey("measurements")) {
                log.warn("No measurements found for metric {}", metricName);
                return;
            }

            List<Map<String, Object>> measurements = (List<Map<String, Object>>) metricDetailMap.get("measurements");
            if (measurements == null || measurements.isEmpty()) {
                log.warn("Measurements list is empty for metric {}", metricName);
                return;
            }

            Map<String, Object> firstMeasurement = measurements.get(0);
            String value = firstMeasurement.get("value").toString();

            MetricProducerEvent metricEvent = MetricProducerEvent.builder()
                    .name(metricName)
                    .value(value)
                    .timestamp(Instant.now().toString())
                    .build();

//            metricsProducerService.sendMetric(metricEvent);
            sendMetricsToKafka(metricEvent);

        } catch (Exception e) {
            log.error("Failed to collect metric {}: {}", metricName, e.getMessage());
        }
    }

    private void sendMetricsToKafka(MetricProducerEvent event) {
        CompletableFuture<SendResult<String, MetricProducerEvent>> future = kafkaTemplate
                .send(metricTopic, event.getId(), event);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Fail to sent metric: {}", exception.getMessage());
                throw new MetricSendException("Fail to sent metric: " + exception.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata().toString());
            }
        });
    }

}
