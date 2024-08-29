package ru.koryruno.MetricsProducerMicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.koryruno.MetricsProducerMicroservice.model.dto.MetricProducerDto;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ActuatorMetricsService {

    private final RestTemplate restTemplate;
    private final MetricProducerService metricsProducerService;

    // TODO come back with question how much fixedRate is needed
//    @Scheduled(fixedRate = 5000)
    public void collectAndSendMetrics() {
        ResponseEntity<Map> metrics = restTemplate
                .getForEntity("http://localhost:8080/actuator/metrics", Map.class);

        metrics.getBody().forEach((name, value) -> {
            MetricProducerDto metric = MetricProducerDto.builder()
                    .name(name.toString())
                    .value(value.toString())
                    .timestamp(Instant.now().toString())
                    .build();

            metricsProducerService.sendMetric(metric);
        });
    }

}
