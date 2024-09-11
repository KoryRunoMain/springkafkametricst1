package ru.koryruno.MetricsProducerMicroservice.model;

public class MetricMapper {

    public static MetricProducerEvent toMetricProducerEvent(ActuatorMetricsDto metricsDto) {
        return MetricProducerEvent.builder()
                .name(metricsDto.getName())
                .value(metricsDto.getValue())
                .timestamp(metricsDto.getTimestamp())
                .build();
    }

}
