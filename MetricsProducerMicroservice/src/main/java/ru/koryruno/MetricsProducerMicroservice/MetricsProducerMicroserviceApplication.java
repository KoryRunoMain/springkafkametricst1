package ru.koryruno.MetricsProducerMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetricsProducerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricsProducerMicroserviceApplication.class, args);
	}

}
