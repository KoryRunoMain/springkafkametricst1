package ru.koryruno.MetricsConsumerMicroservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricDocument;

public interface MetricsDocumentRepository extends MongoRepository<MetricDocument, String> {

}
