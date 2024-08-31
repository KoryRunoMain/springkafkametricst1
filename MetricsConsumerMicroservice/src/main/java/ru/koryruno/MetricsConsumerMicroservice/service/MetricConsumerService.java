package ru.koryruno.MetricsConsumerMicroservice.service;

import ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;

import java.util.List;

public interface MetricConsumerService {

    void saveMetrics(MetricProducerEvent metricProducerEvent);

    MetricResponse getMetricsById(String metricId);

    List<MetricResponse> getAllMetrics();

}
