package ru.koryruno.MetricsProducerMicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.koryruno.MetricsProducerMicroservice.exception.MetricSendException;
import ru.koryruno.MetricsProducerMicroservice.model.ActuatorMetricsDto;
import ru.koryruno.MetricsProducerMicroservice.model.MetricMapper;
import ru.koryruno.MetricsProducerMicroservice.model.MetricProducerEvent;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Service implementation for collecting and sending application metrics.
 * <p>
 * This service interacts with the application's actuator endpoint to collect
 * metrics and then sends them to a Kafka topic.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MetricStatServiceImpl implements MetricStatService {

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

    @Value("${server.port}")
    private String producerPort;

    private final String ACTUATOR_METRICS_URI = "http://localhost:8082/actuator/metrics";
    private final KafkaTemplate<String, MetricProducerEvent> kafkaTemplate;
    private final RestTemplate restTemplate;

    /**
     * Collects metrics from the actuator endpoint and sends them to a Kafka topic.
     * <p>
     * This method retrieves the list of metric names from the actuator's metrics endpoint,
     * and for each metric name, it collects detailed data and sends it to Kafka.
     * </p>
     */
    @Override
    public void collectAndSendMetrics() {
        try {
            ResponseEntity<Map> metricsResponse = restTemplate.getForEntity(ACTUATOR_METRICS_URI, Map.class);
            Map<String, Object> metricsMap = metricsResponse.getBody();

            if (metricsMap == null || !metricsMap.containsKey("names")) {
                log.warn("Failed, no metric names found");
                return;
            }

            List<String> metricNames = (List<String>) metricsMap.get("names");
            metricNames.forEach(this::collectAndSendMetric);

        } catch (Exception e) {
            log.error("Failed to collect and send metrics: {}", e.getMessage());
        }
    }

    /**
     * Collects detailed metric data for a specific metric and sends it to Kafka.
     * <p>
     * This method retrieves detailed information for a given metric name from the actuator endpoint,
     * then creates a DTO object and sends it to the Kafka topic.
     * </p>
     *
     * @param metricName the name of the metric to collect
     */
    private void collectAndSendMetric(String metricName) {
        try {
            ResponseEntity<Map> metricDetailResponse = restTemplate.getForEntity(
                    ACTUATOR_METRICS_URI + "/" + metricName, Map.class);

            Map<String, Object> metricDetailMap = metricDetailResponse.getBody();

            if (metricDetailMap == null || !metricDetailMap.containsKey("measurements")) {
                log.warn("Failed, no measurements found for metric {}", metricName);
                return;
            }

            List<Map<String, Object>> measurements = (List<Map<String, Object>>) metricDetailMap.get("measurements");
            if (measurements == null || measurements.isEmpty()) {
                log.warn("Failed, measurements list is empty for metric {}", metricName);
                return;
            }

            Map<String, Object> firstMeasurement = measurements.get(0);
            String value = firstMeasurement.get("value").toString();

            ActuatorMetricsDto metricsDto = ActuatorMetricsDto.builder()
                    .name(metricName)
                    .value(value)
                    .timestamp(Instant.now().toString())
                    .build();

            sendMetricsToKafka(metricsDto);

        } catch (Exception e) {
            log.error("Failed to collect metric {}: {}", metricName, e.getMessage());
        }
    }

    /**
     * Sends collected metrics data to Kafka.
     * <p>
     * Converts the metrics data into a {@link MetricProducerEvent} and sends it to the configured Kafka topic.
     * Uses a {@link CompletableFuture} to handle the asynchronous send result and logs the outcome.
     * </p>
     *
     * @param metricsDto the metrics data to be sent to Kafka
     * @throws MetricSendException if sending the metric to Kafka fails
     */
    private void sendMetricsToKafka(ActuatorMetricsDto metricsDto) {
        MetricProducerEvent metricEvent = MetricMapper.toMetricProducerEvent(metricsDto);

        CompletableFuture<SendResult<String, MetricProducerEvent>> future = kafkaTemplate
                .send(metricTopic, metricEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to sent metric: {}", exception.getMessage());
                throw new MetricSendException("Fail to sent metric: " + exception.getMessage());

            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata().topic());
            }
        });
    }

}
