package ru.koryruno.MetricsProducerMicroservice.service;

import ru.koryruno.MetricsProducerMicroservice.exception.MetricSendException;
import ru.koryruno.MetricsProducerMicroservice.model.dto.MetricProducerDto;

public interface MetricProducerService {

    String sendMetric(MetricProducerDto metricProducerDto);
}
