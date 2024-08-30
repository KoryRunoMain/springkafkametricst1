package ru.koryruno.MetricsConsumerMicroservice.service;

import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;
import ru.koryruno.coreMetric.MetricProducerEvent;

import java.util.List;

public interface MetricConsumerService {

    void saveMetrics(MetricProducerEvent metricProducerEvent);

    MetricResponse getMetricsById(Long metricId);

    List<MetricResponse> getAllMetrics();

}
