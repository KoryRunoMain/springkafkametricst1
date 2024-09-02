package ru.koryruno.MetricsConsumerMicroservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

/**
 * REST controller for managing and retrieving metrics.
 *
 * <p>
 * This controller provides endpoints for fetching individual metrics by their ID
 * or retrieving all available metrics. It utilizes the {@link MetricConsumerService}
 * to interact with the backend service that handles the logic of fetching the metrics.
 * </p>
 *
 * <p>
 * The controller is annotated with OpenAPI annotations for generating API documentation
 * and facilitating integration with tools like Swagger.
 * </p>
 */
@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AdminMetricController {

    private final MetricConsumerService metricConsumerService;

    @Operation(summary = "Get Metric by ID",
            description = "Fetches a metric by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Metric found",
                            content = @Content(schema = @Schema(implementation = MetricResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Metric not found")
            })
    @GetMapping(path = "/metrics/{metricId}")
    @ResponseStatus(HttpStatus.OK)
    public MetricResponse getMetrics(@PathVariable String metricId) {
        log.info("Successfully get metrics by id: " + metricId);
        return metricConsumerService.getMetricsById(metricId);
    }

    @Operation(summary = "Get All Metrics",
            description = "Fetches all metrics available.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Metrics fetched successfully",
                            content = @Content(schema = @Schema(type = "array", implementation = MetricResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping(path = "/metrics")
    @ResponseStatus(HttpStatus.OK)
    public List<MetricResponse> getAllMetrics() {
        log.info("Successfully get all metrics");
        return metricConsumerService.getAllMetrics();
    }

}
