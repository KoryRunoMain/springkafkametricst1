package ru.koryruno.MetricsConsumerMicroservice.model;

import org.postgresql.core.Parser;
import ru.koryruno.coreMetric.MetricProducerEvent;

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

    public static Metrics tiMetrics(MetricProducerEvent metricProducerEvent) {
        return Metrics.builder()
                .name(metricProducerEvent.getName())
                .value(metricProducerEvent.getValue())
                .timestamp(metricProducerEvent.getTimestamp())
                .build();
    }

}
