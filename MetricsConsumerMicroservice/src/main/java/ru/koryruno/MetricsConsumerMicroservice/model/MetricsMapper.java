package ru.koryruno.MetricsConsumerMicroservice.model;

import java.util.List;

public class MetricsMapper {

    public static MetricResponse toMetricResponse(Metrics metrics) {
        return MetricResponse.builder()
                .id(metrics.getId())
                .name(metrics.getName())
                .value(metrics.getValue())
                .timestamp(metrics.getTimestamp())
                .build();
    }

}
