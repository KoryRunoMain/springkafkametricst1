package ru.koryruno.MetricsConsumerMicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.koryruno.MetricsConsumerMicroservice.model.Metrics;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {

}
