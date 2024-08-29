package ru.koryruno.MetricsProducerMicroservice.model;

import ru.koryruno.MetricsProducerMicroservice.model.dto.MetricProducerDto;
import ru.koryruno.coreMetric.MetricProducerEvent;

public class MetricMapper {

//    public static MetricProducer toMetric(MetricProducerDto metricDto) {
//        return MetricProducer.builder()
//                .name(metricDto.getName())
//                .value(metricDto.getValue())
//                .timestamp(metricDto.getTimestamp())
//                .build();
//    }

    public static MetricProducerEvent toMetricEvent(String metricId, MetricProducerDto metricDto) {
        MetricProducerEvent metricProducerEvent = new MetricProducerEvent();
        metricProducerEvent.setId(metricId);
        metricProducerEvent.setName(metricDto.getName());
        metricProducerEvent.setValue(metricDto.getValue());
        metricProducerEvent.setTimestamp(metricDto.getTimestamp());
        return metricProducerEvent;
    }

//    public static MetricProducerDto toMetricDto(MetricProducer metric) {
//        return MetricProducerDto.builder()
//                .name(metric.getName())
//                .value(metric.getValue())
//                .timestamp(metric.getTimestamp())
//                .build();
//    }

}
