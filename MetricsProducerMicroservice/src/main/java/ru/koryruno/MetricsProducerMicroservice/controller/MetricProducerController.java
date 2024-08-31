package ru.koryruno.MetricsProducerMicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.koryruno.MetricsProducerMicroservice.service.MetricStatService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MetricProducerController {

    private final MetricStatService metricStatService;

    @GetMapping(path = "/metrics")
    public ResponseEntity<String> getMetrics() {
        try {
            metricStatService.collectAndSendMetrics();
            return ResponseEntity.status(HttpStatus.OK).body("Successfully send metrics");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send metrics");
        }
    }

}
