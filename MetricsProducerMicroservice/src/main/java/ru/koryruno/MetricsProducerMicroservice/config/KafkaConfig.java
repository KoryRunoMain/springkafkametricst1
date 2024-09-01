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

/**
 * Kafka configuration class to set up Kafka producers. Also create Kafka-Topic.
 *
 * <p>
 * This class configures Kafka producer settings and defines Kafka topics.
 * </p>
 */
@Configuration
public class KafkaConfig {

    @Autowired
    public Environment environment;

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

    /**
     * Creates a Kafka topic with specified configurations.
     * <p>
     * The topic is configured with 3 partitions and 3 replicas. The topic also has a configuration
     * to ensure a minimum of 2 replicas are in synchronized state.
     * </p>
     *
     * @return a {@link NewTopic} object representing the created topic
     */
    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(metricTopic)
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    /**
     * Configures the Kafka producer settings.
     *
     * @return a {@link Map} containing Kafka producer configuration properties
     */
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

    /**
     * Configures the Kafka producer factory.
     * <p>
     * This method creates a {@link ProducerFactory} configured with the settings from {@link #producerConfigs()}.
     * </p>
     *
     * @return a {@link ProducerFactory} for creating Kafka producers
     */
    @Bean
    public ProducerFactory<String, MetricProducerEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Configures the Kafka template for sending messages.
     * <p>
     * This method creates a {@link KafkaTemplate} configured with the {@link ProducerFactory} created by {@link #producerFactory()}.
     * </p>
     *
     * @return a {@link KafkaTemplate} for sending Kafka messages
     */
    @Bean
    public KafkaTemplate<String, MetricProducerEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
