package ru.koryruno.MetricsProducerMicroservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.koryruno.MetricsProducerMicroservice.service.MetricStatService;

/**
 * REST controller for handling metrics collection and sending operations.
 *
 * <p>
 * This controller exposes an endpoint that triggers the collection of metrics
 * from an external source and sends them to a Kafka topic. The result of the
 * operation is returned as a response.
 * </p>
 *
 * <p>
 * The controller is documented using OpenAPI annotations to provide a detailed
 * API description and to facilitate integration with tools like Swagger.
 * </p>
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MetricProducerController {

    private final MetricStatService metricStatService;

    @Operation(summary = "Collect and Send Metrics",
            description = "Collects and sends metrics, returning success or error message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully sent metrics"),
            @ApiResponse(responseCode = "500", description = "Failed to send metrics")
    })
    @GetMapping(path = "/metrics")
    public ResponseEntity<String> getMetrics() {
        try {
            metricStatService.collectAndSendMetrics();
            return ResponseEntity.status(HttpStatus.OK).body("Successfully sent metrics");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to sent metrics");
        }
    }

}
