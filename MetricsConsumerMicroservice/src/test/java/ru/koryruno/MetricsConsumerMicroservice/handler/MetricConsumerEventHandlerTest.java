package ru.koryruno.MetricsConsumerMicroservice.handler;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent;
import ru.koryruno.MetricsConsumerMicroservice.service.MetricConsumerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MetricConsumerEventHandlerTest {

    @Mock
    private MetricConsumerService metricConsumerService;

    @InjectMocks
    private MetricConsumerEventHandler metricConsumerEventHandler;

    private static final MetricProducerEvent event = MetricProducerEvent.builder()
            .name("testMetric")
            .value("100.0")
            .timestamp("2024-09-01T17:03:42.255624700Z")
            .build();

    @Test
    void When_HandleValidMetricProducerEvent_Expect_Successfully() {
        metricConsumerEventHandler.handle(event);

        verify(metricConsumerService, times(1)).saveMetrics(event);
    }

    @Test
    void When_HandleNullMetricProducerEvent_Expect_SaveMetricsIsNotCalled() {
        metricConsumerEventHandler.handle(null);

        verify(metricConsumerService, never()).saveMetrics(any(MetricProducerEvent.class));
    }

    @Test
    void When_HandleDlt_Expect_LogError_Successfully() {
        MetricProducerEvent event = new MetricProducerEvent();
        event.setName("testMetric");
        String topic = "testTopic";
        long offset = 123L;
        metricConsumerEventHandler.handleDlt(event, topic, offset);

        verify(metricConsumerService, times(0)).saveMetrics(event);
    }

}
