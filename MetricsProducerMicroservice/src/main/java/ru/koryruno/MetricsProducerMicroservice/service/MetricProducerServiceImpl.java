package ru.koryruno.MetricsProducerMicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.koryruno.MetricsProducerMicroservice.model.MetricMapper;
import ru.koryruno.MetricsProducerMicroservice.model.dto.MetricProducerDto;
import ru.koryruno.coreMetric.MetricProducerEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricProducerServiceImpl implements MetricProducerService {

    private final KafkaTemplate<String, MetricProducerEvent> kafkaTemplate;

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

    @Override
    public String sendMetric(MetricProducerDto metricProducerDto) {
//        String metricId = UUID.randomUUID().toString();
        MetricProducerEvent metricProducerEvent = MetricMapper.toMetricEvent(metricProducerDto);

        CompletableFuture<SendResult<String, MetricProducerEvent>> future = kafkaTemplate
                .send(metricTopic, metricProducerEvent.getId(), metricProducerEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Fail to sent metric: {}", exception.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata().toString());
            }
        });

        return metricProducerEvent.getId();
    }
}
