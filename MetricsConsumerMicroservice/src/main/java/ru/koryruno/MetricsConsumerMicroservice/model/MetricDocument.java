package ru.koryruno.MetricsConsumerMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A document representing a metric stored in the MongoDB collection.
 *
 * <p>
 * This class is used to persist metric data in a MongoDB database.
 * It includes an ID, the name of the metric, its value, and the timestamp
 * when the metric was recorded.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "metrics")
public class MetricDocument {

    @Id
    @Indexed
    private String id;
    private String name;
    private String value;
    private String timestamp;

}
