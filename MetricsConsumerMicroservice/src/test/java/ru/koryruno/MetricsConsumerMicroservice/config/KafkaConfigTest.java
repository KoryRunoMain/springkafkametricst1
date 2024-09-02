package ru.koryruno.MetricsConsumerMicroservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class KafkaConfigTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private KafkaConfig kafkaConfig;

    @BeforeEach
    void setUp() {
        lenient().when(environment.getProperty("spring.kafka.consumer.bootstrap-servers"))
                .thenReturn("localhost:9092");
        lenient().when(environment.getProperty("default.value.type")).
                thenReturn("ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent");
        lenient().when(environment.getProperty("spring.kafka.consumer.group-id"))
                .thenReturn("test-group-id");
    }

    @Test
    void When_ConsumerFactory_Expect_Successfully() {
        ConsumerFactory<String, Object> consumerFactory = kafkaConfig.consumerFactory();

        assertNotNull(consumerFactory);

        Map<String, Object> expectedConfigs = new HashMap<>();
        expectedConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        expectedConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        expectedConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        expectedConfigs.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        expectedConfigs.put(JsonDeserializer.VALUE_DEFAULT_TYPE,
                "ru.koryruno.MetricsConsumerMicroservice.model.MetricProducerEvent");
        expectedConfigs.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        expectedConfigs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        expectedConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-id");
    }

    @Test
    void When_ProducerFactory_Expect_Successfully() {
        ProducerFactory<String, Object> producerFactory = kafkaConfig.producerFactory();

        assertNotNull(producerFactory);

        Map<String, Object> expectedConfigs = new HashMap<>();
        expectedConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        expectedConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        expectedConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    }

    @Test
    void When_KafkaTemplate_Expect_Successfully() {
        ProducerFactory<String, Object> producerFactory = mock(ProducerFactory.class);
        KafkaTemplate<String, Object> kafkaTemplate = kafkaConfig.kafkaTemplate(producerFactory);

        assertNotNull(kafkaTemplate);
        assertSame(kafkaTemplate.getProducerFactory(), producerFactory);
    }

}