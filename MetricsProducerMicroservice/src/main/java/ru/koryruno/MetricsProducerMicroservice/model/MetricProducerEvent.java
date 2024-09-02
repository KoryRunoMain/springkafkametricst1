package ru.koryruno.MetricsProducerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an event containing metric data to be sent to a Kafka topic.
 * <p>
 * This class is used as a data transfer object (DTO) for transmitting metric information
 * from the application to the Kafka messaging system.
 * </p>
 * <p>
 * The event includes the metric's name, value, and timestamp at which the metric was collected.
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
