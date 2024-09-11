package ru.koryruno.MetricsConsumerMicroservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Kory Runo",
                        email = "urajimirudesu@gmail.com"
                ),
                description = "OpenApi documentation for T1 homework project: monitoring app with Spring Kafka",
                title = "MetricsConsumerMicroservice specification",
                version = "0.0.1-SnapShot"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8081"
                )
        }
)
public class OpenApiConfig {
}