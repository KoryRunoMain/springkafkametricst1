package ru.koryruno.MetricsConsumerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricProducerEvent {

    private String id;
    private String name;
    private String value;
    private String timestamp;

}
