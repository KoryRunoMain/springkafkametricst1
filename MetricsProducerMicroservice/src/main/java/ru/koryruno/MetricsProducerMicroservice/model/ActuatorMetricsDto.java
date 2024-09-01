package ru.koryruno.MetricsProducerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for storing and transferring metrics data collected from the actuator.
 * <p>
 * This class is used to encapsulate metric data retrieved from the actuator's endpoints.
 * The data typically includes the metric's name, its value, and the timestamp when the metric was collected.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActuatorMetricsDto {

    private String name;
    private String value;
    private String timestamp;

}
