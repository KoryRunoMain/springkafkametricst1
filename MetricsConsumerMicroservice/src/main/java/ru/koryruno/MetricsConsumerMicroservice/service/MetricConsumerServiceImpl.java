package ru.koryruno.MetricsConsumerMicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.koryruno.MetricsConsumerMicroservice.exception.NotFoundException;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricDocument;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricMapper;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;
import ru.koryruno.MetricsConsumerMicroservice.repository.MetricsDocumentRepository;

import java.util.List;

/**
 * Service implementation for handling metric data received from Kafka.
 *
 * <p>
 * This service provides functionality for saving metrics to a database
 * and retrieving them by ID or all at once.
 * It utilizes the {@link MetricsDocumentRepository} to interact with the database
 * and {@link MetricMapper} to map between different data models.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MetricConsumerServiceImpl implements MetricConsumerService {

    private final MetricsDocumentRepository metricsDocumentRepository;

    @Override
    public void saveMetrics(MetricProducerEvent metricProducerEvent) {
        MetricDocument document = MetricMapper.toMetricDocument(metricProducerEvent);
        metricsDocumentRepository.save(document);
        log.info("Successfully saved metric name {}", document.getName());
    }

    @Override
    public MetricResponse getMetricsById(String metricId) {
        return MetricMapper.toMetricResponse(
                metricsDocumentRepository.findById(metricId)
                        .orElseThrow(() -> new NotFoundException("Not Found metric with id: " + metricId)));
    }

    @Override
    public List<MetricResponse> getAllMetrics() {
        return metricsDocumentRepository.findAll().stream()
                .map(MetricMapper::toMetricResponse)
                .toList();
    }

}
