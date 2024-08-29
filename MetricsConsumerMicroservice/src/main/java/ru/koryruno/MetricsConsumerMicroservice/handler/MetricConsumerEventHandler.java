package ru.koryruno.MetricsConsumerMicroservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.koryruno.coreMetric.MetricProducerEvent;

@Component
@Slf4j
@KafkaListener(topics = "${kafka.topic.metrics}")
public class MetricConsumerEventHandler {

    @KafkaHandler
    public void handle(MetricProducerEvent metricProducerEvent) {
        log.info("Received event: {}", metricProducerEvent.getName());
    }

}
