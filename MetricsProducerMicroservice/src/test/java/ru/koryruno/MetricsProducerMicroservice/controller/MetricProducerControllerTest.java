package ru.koryruno.MetricsProducerMicroservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.koryruno.MetricsProducerMicroservice.service.MetricStatServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(MetricProducerController.class)
class MetricProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricStatServiceImpl metricStatService;

    private static final String BASE_URI_FOR_METRICS = "/metrics";

    @Test
    public void When_GetMetrics_Expect_Successfully() throws Exception {
        doNothing().when(metricStatService).collectAndSendMetrics();

        mockMvc.perform(get(BASE_URI_FOR_METRICS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully send metrics"));
    }

    @Test
    public void When_GetMetrics_Expect_FailedToSendMetrics() throws Exception {
        doThrow(new RuntimeException("Error")).when(metricStatService).collectAndSendMetrics();

        mockMvc.perform(get(BASE_URI_FOR_METRICS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to sent metrics"));
    }

}
