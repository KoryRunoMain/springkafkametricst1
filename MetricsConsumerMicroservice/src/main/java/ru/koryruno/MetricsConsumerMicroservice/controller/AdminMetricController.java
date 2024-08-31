package ru.koryruno.MetricsConsumerMicroservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;
import ru.koryruno.MetricsConsumerMicroservice.service.MetricConsumerService;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AdminMetricController {

    private final MetricConsumerService metricConsumerService;

    @GetMapping(path = "/metrics/{metricId}")
    @ResponseStatus(HttpStatus.OK)
    public MetricResponse getMetrics(@PathVariable String metricId) {
        log.info("Successfully get metrics by id: " + metricId);
        return metricConsumerService.getMetricsById(metricId);
    }

    @GetMapping(path = "/metrics")
    @ResponseStatus(HttpStatus.OK)
    public List<MetricResponse> getAllMetrics() {
        log.info("Successfully get all metrics");
        return metricConsumerService.getAllMetrics();
    }

}
