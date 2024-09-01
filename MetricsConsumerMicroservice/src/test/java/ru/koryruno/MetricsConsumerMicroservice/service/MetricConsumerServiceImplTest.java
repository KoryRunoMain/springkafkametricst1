package ru.koryruno.MetricsConsumerMicroservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.koryruno.MetricsConsumerMicroservice.exception.NotFoundException;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricDocument;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;
import ru.koryruno.MetricsConsumerMicroservice.repository.MetricsDocumentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricConsumerServiceImplTest {

    @Mock
    private MetricsDocumentRepository metricsDocumentRepository;

    @InjectMocks
    private MetricConsumerServiceImpl metricConsumerService;

    private static final MetricProducerEvent event = MetricProducerEvent.builder()
            .name("testMetric")
            .value("100.0")
            .timestamp("2024-09-01T17:03:42.255624700Z")
            .build();

    @Test
    void When_SaveMetrics_Expect_Successfully() {
        metricConsumerService.saveMetrics(event);

        verify(metricsDocumentRepository, times(1)).save(any(MetricDocument.class));

        ArgumentCaptor<MetricDocument> captor = ArgumentCaptor.forClass(MetricDocument.class);
        verify(metricsDocumentRepository).save(captor.capture());
        MetricDocument capturedDocument = captor.getValue();

        assertEquals("testMetric", capturedDocument.getName());
        assertEquals("100.0", capturedDocument.getValue());
        assertEquals("2024-09-01T17:03:42.255624700Z", capturedDocument.getTimestamp());
    }

    @Test
    void When_GetMetricsById_Expect_Successfully() {
        when(metricsDocumentRepository.findById(anyString())).thenReturn(Optional.of(new MetricDocument()));

        MetricResponse response = metricConsumerService.getMetricsById("testId");

        assertNotNull(response);
        verify(metricsDocumentRepository, times(1)).findById(anyString());
    }

    @Test
    void When_GetMetricsById_WithWrongId_Expect_NotFoundException() {
        when(metricsDocumentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> metricConsumerService.getMetricsById("invalidId"));
        verify(metricsDocumentRepository, times(1)).findById(anyString());
    }

    @Test
    void When_GetAllMetrics_Expect_Successfully() {
        when(metricsDocumentRepository.findAll()).thenReturn(List.of(new MetricDocument(), new MetricDocument()));

        List<MetricResponse> responses = metricConsumerService.getAllMetrics();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(metricsDocumentRepository, times(1)).findAll();
    }

}