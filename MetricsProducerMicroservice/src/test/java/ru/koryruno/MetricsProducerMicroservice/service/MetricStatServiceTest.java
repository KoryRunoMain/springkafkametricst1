package ru.koryruno.MetricsProducerMicroservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.client.RestTemplate;
import ru.koryruno.MetricsProducerMicroservice.model.MetricProducerEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MetricStatServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private KafkaTemplate<String, MetricProducerEvent> kafkaTemplate;

    @InjectMocks
    private MetricStatServiceImpl metricStatService;

    private static final String ACTUATOR_URI = "http://localhost:9091/actuator/metrics";
    private static final String ACTUATOR_URI_WITH_METRIC_1 = "http://localhost:9091/actuator/metrics/metric1";
    private static final String ACTUATOR_URI_WITH_METRIC_2 = "http://localhost:9091/actuator/metrics/metric2";

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

    @Test
    public void When_CollectAndSendMetrics_WithMetricsNames_Expect_Successfully() {
        Map<String, Object> metricsResponseBody = new HashMap<>();
        metricsResponseBody.put("names", Arrays.asList("metric1", "metric2"));

        Map<String, Object> metricDetailResponseBody = new HashMap<>();
        metricDetailResponseBody.put("measurements", Arrays.asList(Map.of("value", 1125124)));

        ResponseEntity<Map> metricsResponse = ResponseEntity.ok(metricsResponseBody);
        when(restTemplate.getForEntity(eq(ACTUATOR_URI), eq(Map.class))).thenReturn(metricsResponse);

        ResponseEntity<Map> metricDetailResponse = ResponseEntity.ok(metricDetailResponseBody);
        when(restTemplate.getForEntity(eq(ACTUATOR_URI_WITH_METRIC_1), eq(Map.class))).thenReturn(metricDetailResponse);
        when(restTemplate.getForEntity(eq(ACTUATOR_URI_WITH_METRIC_2), eq(Map.class))).thenReturn(metricDetailResponse);

        CompletableFuture<SendResult<String, MetricProducerEvent>> future = CompletableFuture
                .completedFuture(new SendResult<>(null, null));
        when(kafkaTemplate.send(eq(metricTopic), any(MetricProducerEvent.class))).thenReturn(future);

        metricStatService.collectAndSendMetrics();

        verify(kafkaTemplate, times(2)).send(eq(metricTopic), any(MetricProducerEvent.class));
    }

    @Test
    public void When_CollectAndSendMetrics_WithoutMetricsNames_Expect_NoMessagesSentToKafka() {
        Map<String, Object> metricsResponseBody = new HashMap<>();
        ResponseEntity<Map> metricsResponse = ResponseEntity.ok(metricsResponseBody);
        when(restTemplate.getForEntity(eq(ACTUATOR_URI), eq(Map.class))).thenReturn(metricsResponse);

        metricStatService.collectAndSendMetrics();

        verify(kafkaTemplate, never()).send(eq(metricTopic), any(MetricProducerEvent.class));
    }

    @Test
    public void When_CollectAndSendMetrics_RestTemplateThrowsException_Expect_NoMessagesSentToKafka() {
        when(restTemplate.getForEntity(eq(ACTUATOR_URI), eq(Map.class))).thenThrow(new RuntimeException("Error"));

        metricStatService.collectAndSendMetrics();

        verify(kafkaTemplate, never()).send(eq(metricTopic), any(MetricProducerEvent.class));
    }

}