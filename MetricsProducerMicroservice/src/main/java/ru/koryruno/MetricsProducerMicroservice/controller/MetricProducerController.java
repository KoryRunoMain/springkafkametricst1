package ru.koryruno.MetricsProducerMicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.koryruno.MetricsProducerMicroservice.model.dto.MetricProducerDto;
import ru.koryruno.MetricsProducerMicroservice.service.MetricProducerService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MetricProducerController {

    private final MetricProducerService metricProducerService;

    @RequestMapping(path = "/metrics")
    public ResponseEntity<String> createMetric(@RequestBody MetricProducerDto metricDto) {
        String metricId = metricProducerService.sendMetric(metricDto);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully, metricId: " + metricId);
    }

}
