package ru.koryruno.MetricsProducerMicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.koryruno.MetricsProducerMicroservice.model.MetricProducerEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Autowired
    public Environment environment;

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(metricTopic)
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.asks"));
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        configProps.put(ProducerConfig.LINGER_MS_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.timeout.ms"));
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.enable.idempotence"));
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,
                environment.getProperty("spring.kafka.producer.properties.max.in.flight.requests.per.connection"));

        return configProps;
    }

    @Bean
    public ProducerFactory<String, MetricProducerEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, MetricProducerEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
