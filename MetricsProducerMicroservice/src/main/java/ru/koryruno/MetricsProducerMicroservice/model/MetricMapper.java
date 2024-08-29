package ru.koryruno.MetricsProducerMicroservice.model;

import ru.koryruno.MetricsProducerMicroservice.model.dto.MetricProducerDto;

public class MetricMapper {

    public static MetricProducer toMetric(MetricProducerDto metricDto) {
        return MetricProducer.builder()
                .name(metricDto.getName())
                .value(metricDto.getValue())
                .timestamp(metricDto.getTimestamp())
                .build();
    }

    public static MetricProducerEvent toMetricEvent(MetricProducerDto metricDto) {
        return MetricProducerEvent.builder()
                .name(metricDto.getName())
                .value(metricDto.getValue())
                .timestamp(metricDto.getTimestamp())
                .build();
    }

    public static MetricProducerDto toMetricDto(MetricProducer metric) {
        return MetricProducerDto.builder()
                .name(metric.getName())
                .value(metric.getValue())
                .timestamp(metric.getTimestamp())
                .build();
    }

}
