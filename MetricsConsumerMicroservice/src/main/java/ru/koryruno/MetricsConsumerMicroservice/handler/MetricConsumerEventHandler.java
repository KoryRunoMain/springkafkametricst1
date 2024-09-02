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

import java.time.Instant;

/**
 * Handler for processing metric events from a Kafka topic.
 *
 * <p>
 * This component listens to a specified Kafka topic for metric events,
 * processes the incoming events and handles any dead-lettered messages.
 * It uses the {@link MetricConsumerService} to persist the received metrics.
 * </p>
 */
@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topic.metrics}")
public class MetricConsumerEventHandler {

    private final MetricConsumerService metricConsumerService;

    /**
     * Handles incoming metric events from the Kafka topic.
     * <p>
     * This method is triggered when a new {@link MetricProducerEvent} is received from the Kafka topic.
     * If the event is valid (non-null), it is passed to the {@link MetricConsumerService} for persistence.
     * </p>
     *
     * @param metricProducerEvent the metric event received from the Kafka topic
     */
    @KafkaHandler
    public void handle(MetricProducerEvent metricProducerEvent) {
        if (metricProducerEvent == null) {
            String metricProdEventTime = Instant.now().toString();
            log.info("Metric is null, message time: {}", metricProdEventTime);
            return;
        }
        metricConsumerService.saveMetrics(metricProducerEvent);
        log.info("Get event name: {}", metricProducerEvent.getName());
    }

    /**
     * Handles messages that were sent to the Dead Letter Topic (DLT).
     * <p>
     * This method is triggered when a message is sent to the DLT due to processing failures.
     * It logs the details of the failed message, including the topic and offset.
     * </p>
     *
     * @param metricProducerEvent the metric event received from the DLT
     * @param topic               the name of the Kafka topic from which the message was originally sent
     * @param offset              the offset of the message in the topic
     */
    @DltHandler
    public void handleDlt(MetricProducerEvent metricProducerEvent,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {

        log.error("Received from DLT: {}, topic: {}, offset: {}", metricProducerEvent.getName(), topic, offset);
    }

}
