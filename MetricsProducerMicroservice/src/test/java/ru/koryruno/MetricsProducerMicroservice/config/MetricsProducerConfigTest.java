package ru.koryruno.MetricsProducerMicroservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MetricsProducerConfigTest {

    @Test
    public void restTemplateBeanExists(ApplicationContext context) {
        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        assertNotNull(restTemplate);
        assertTrue(true);
    }

}
