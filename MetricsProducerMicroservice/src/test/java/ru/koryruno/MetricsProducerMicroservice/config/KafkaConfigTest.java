package ru.koryruno.MetricsProducerMicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.koryruno.MetricsProducerMicroservice.model.MetricProducerEvent;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class KafkaConfigTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private KafkaConfig kafkaConfig;

    @Value("${kafka.topic.metrics}")
    private String metricTopic;

    @BeforeEach
    void setUp() {
        lenient().when(environment.getProperty("spring.kafka.producer.bootstrap-servers")).thenReturn("localhost:9092");
        lenient().when(environment.getProperty("spring.kafka.producer.asks")).thenReturn("all");
        lenient().when(environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms")).thenReturn("30000");
        lenient().when(environment.getProperty("spring.kafka.producer.properties.linger.ms")).thenReturn("1");
        lenient().when(environment.getProperty("spring.kafka.producer.properties.timeout.ms")).thenReturn("5000");
        lenient().when(environment.getProperty("spring.kafka.producer.properties.enable.idempotence")).thenReturn("true");
        lenient().when(environment.getProperty("spring.kafka.producer.properties.max.in.flight.requests.per.connection")).thenReturn("5");
    }

    @Test
    void When_ProducerConfigs_Expect_Successfully() {
        Map<String, Object> configs = kafkaConfig.producerConfigs();
        assertEquals("localhost:9092", configs.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringSerializer.class, configs.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        assertEquals(JsonSerializer.class, configs.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
        assertEquals("all", configs.get(ProducerConfig.ACKS_CONFIG));
        assertEquals("30000", configs.get(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG));
        assertEquals("1", configs.get(ProducerConfig.LINGER_MS_CONFIG));
        assertEquals("5000", configs.get(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG));
        assertEquals("true", configs.get(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG));
        assertEquals("5", configs.get(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION));
    }

    @Test
    public void When_CreateTopic_Successfully() {
        NewTopic topic = kafkaConfig.createTopic();
        assertNotNull(topic);
        assertEquals(metricTopic, topic.name());
        assertEquals(3, topic.numPartitions());
        assertEquals(3, topic.replicationFactor());
        assertEquals("2", topic.configs().get("min.insync.replicas"));
    }

    @Test
    public void When_ProducerFactory_Expect_Successfully() {
        ProducerFactory<String, MetricProducerEvent> producerFactory = kafkaConfig.producerFactory();
        assertNotNull(producerFactory);
        assertEquals(DefaultKafkaProducerFactory.class, producerFactory.getClass());
    }

    @Test
    public void When_KafkaTemplate_Expect_Successfully() {
        KafkaTemplate<String, MetricProducerEvent> kafkaTemplate = kafkaConfig.kafkaTemplate();
        assertNotNull(kafkaTemplate);
    }

}
