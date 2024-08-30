package ru.koryruno.MetricsConsumerMicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.koryruno.MetricsConsumerMicroservice.exception.NotFoundException;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;
import ru.koryruno.MetricsConsumerMicroservice.model.Metrics;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricsMapper;
import ru.koryruno.MetricsConsumerMicroservice.repository.MetricsRepository;
import ru.koryruno.coreMetric.MetricProducerEvent;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MetricConsumerServiceImpl implements MetricConsumerService {

    private final MetricsRepository metricsRepository;

    @Override
    public void saveMetrics(MetricProducerEvent metricProducerEvent) {
        Metrics metrics = Metrics.builder()
                .name(metricProducerEvent.getName())
                .value(metricProducerEvent.getValue())
                .timestamp(metricProducerEvent.getTimestamp())
                .build();

        metricsRepository.save(metrics);
        log.info("Successfully saved metrics name: {}, with incoming id: {}", metrics.getName(), metrics.getId());
    }

    @Override
    public MetricResponse getMetricsById(Long metricId) {
        return MetricsMapper.toMetricResponse(metricsRepository.findById(metricId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Not Found metrics with id: %d", metricId))));
    }

    @Override
    public List<MetricResponse> getAllMetrics() {
        return metricsRepository.findAll().stream()
                .map(MetricsMapper::toMetricResponse)
                .toList();
    }

}
