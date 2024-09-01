package ru.koryruno.MetricsConsumerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object representing a metric response.
 *
 * <p>
 * This class is used to encapsulate the details of a metric,
 * which is returned to the user in response to their request.
 * It includes the metrics unique identifier, name, value, and timestamp
 * of when the metric was recorded.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricResponse {

    private String id;
    private String name;
    private String value;
    private String timestamp;

}
