package ru.koryruno.MetricsConsumerMicroservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent;
import ru.koryruno.MetricsConsumerMicroservice.service.MetricConsumerService;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topic.metrics}")
public class MetricConsumerEventHandler {

    private final MetricConsumerService metricConsumerService;

    @KafkaHandler
    public void handle(MetricProducerEvent metricProducerEvent) {
        metricConsumerService.saveMetrics(metricProducerEvent);
        log.info("Saved event: {}, {}, {}, {}",
                metricProducerEvent.getId(),
                metricProducerEvent.getName(),
                metricProducerEvent.getValue(),
                metricProducerEvent.getTimestamp());
    }

    @DltHandler
    public void handleDlt(MetricProducerEvent metricProducerEvent,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {

        log.error("Received from DLT: {}, topic: {}, offset: {}", metricProducerEvent.getName(), topic, offset);
    }

}
