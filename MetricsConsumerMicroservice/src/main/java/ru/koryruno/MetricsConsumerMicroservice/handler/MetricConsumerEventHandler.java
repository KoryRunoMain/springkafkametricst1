package ru.koryruno.MetricsConsumerMicroservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import ru.koryruno.MetricsConsumerMicroservice.exception.NonRetryableException;
import ru.koryruno.MetricsConsumerMicroservice.exception.RetryableException;
import ru.koryruno.MetricsConsumerMicroservice.service.MetricConsumerService;
import ru.koryruno.coreMetric.MetricProducerEvent;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topic.metrics}")
public class MetricConsumerEventHandler {

    private final MetricConsumerService metricConsumerService;

//    @KafkaHandler
//    public void handle(MetricProducerEvent metricProducerEvent) {
//        log.info("Received event: {}", metricProducerEvent.getName());
//    }

    @RetryableTopic(backoff = @Backoff(delay = 2000, maxDelay = 10000, multiplier = 2))
    @KafkaHandler
    public void handle(MetricProducerEvent metricProducerEvent,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("Received event: {} from topic: {}, offset: {}", metricProducerEvent.getName(), topic, offset);

        try {
            processMetric(metricProducerEvent);
            metricConsumerService.saveMetrics(metricProducerEvent);
        } catch (NonRetryableException e) {
            log.error("Non-retryable error occurred: {}", e.getMessage());
        } catch (RetryableException e) {
            log.warn("Retryable error occurred: {}", e.getMessage());
        }
    }

    @DltHandler
    public void handleDlt(String in,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {

        log.error("Received from DLT: {}, topic: {}, offset: {}", in, topic, offset);
    }

    // TODO question how this will be work. Not sure this code is ok
    private void processMetric(MetricProducerEvent metricProducerEvent) {
        if ("fail".equals(metricProducerEvent.getName())) {
            throw new RetryableException("Retryable exception event: " + metricProducerEvent.getName());
        } else if ("error".equals(metricProducerEvent.getName())) {
            throw new NonRetryableException("Non-retryable exception" + metricProducerEvent.getName());
        }

        log.info("Processed event: {}", metricProducerEvent.getName());
    }

}
