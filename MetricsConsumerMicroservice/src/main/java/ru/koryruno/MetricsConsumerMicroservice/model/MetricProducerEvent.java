package ru.koryruno.MetricsConsumerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object representing a metric event.
 *
 * <p>
 * This class is used to encapsulate the details of a metric that is received from the producer.
 * It includes the name of the metric, its value, and the timestamp when the metric was recorded.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricProducerEvent {

    private String name;
    private String value;
    private String timestamp;

}
