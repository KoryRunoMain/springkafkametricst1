package ru.koryruno.MetricsConsumerMicroservice.model;

public class MetricMapper {

    public static MetricDocument toMetricDocument(MetricProducerEvent metricProducerEvent) {
        return MetricDocument.builder()
                .name(metricProducerEvent.getName())
                .value(metricProducerEvent.getValue())
                .timestamp(metricProducerEvent.getTimestamp())
                .build();
    }

    public static MetricResponse toMetricResponse(MetricDocument metricDocument) {
        return MetricResponse.builder()
                .id(metricDocument.getId())
                .name(metricDocument.getName())
                .value(metricDocument.getValue())
                .timestamp(metricDocument.getTimestamp())
                .build();
    }

}
