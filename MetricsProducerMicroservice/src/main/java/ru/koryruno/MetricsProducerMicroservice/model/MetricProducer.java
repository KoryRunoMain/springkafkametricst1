package ru.koryruno.MetricsProducerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricProducer {

    private String id;
    private String name;
    private String value;
    private String timestamp;

}
