package ru.koryruno.MetricsConsumerMicroservice.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.koryruno.MetricsConsumerMicroservice.model.MetricResponse;
import ru.koryruno.MetricsConsumerMicroservice.service.MetricConsumerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminMetricController.class)
class AdminMetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricConsumerService metricConsumerService;

    private final MetricResponse mockResponse = MetricResponse.builder()
            .name("testMetric")
            .value("100.0")
            .timestamp("2024-09-01T17:03:42.255624700Z")
            .build();

    private final MetricResponse response1 = MetricResponse.builder()
            .name("metric1")
            .value("100.0")
            .timestamp("2024-09-01T17:03:42.255624700Z")
            .build();

    private final MetricResponse response2 = MetricResponse.builder()
            .name("metric2")
            .value("200.0")
            .timestamp("2024-09-01T17:03:46.255624700Z")
            .build();

    @Test
    void When_GetMetricsById_Expect_Successfully() throws Exception {
        Mockito.when(metricConsumerService.getMetricsById(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/metrics/{metricId}", "testId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("testMetric")))
                .andExpect(jsonPath("$.value", is("100.0")))
                .andExpect(jsonPath("$.timestamp", is("2024-09-01T17:03:42.255624700Z")));
    }

    @Test
    void When_GetAllMetrics_Expect_Successfully() throws Exception {
        List<MetricResponse> mockResponseList = Arrays.asList(response1, response2);

        Mockito.when(metricConsumerService.getAllMetrics()).thenReturn(mockResponseList);

        mockMvc.perform(get("/metrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("metric1")))
                .andExpect(jsonPath("$[0].value", is("100.0")))
                .andExpect(jsonPath("$[0].timestamp", is("2024-09-01T17:03:42.255624700Z")))
                .andExpect(jsonPath("$[1].name", is("metric2")))
                .andExpect(jsonPath("$[1].value", is("200.0")))
                .andExpect(jsonPath("$[1].timestamp", is("2024-09-01T17:03:46.255624700Z")));
    }

}
