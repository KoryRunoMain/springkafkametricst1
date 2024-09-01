FROM openjdk:17-jdk-slim
COPY target/metrics-consumer-microservice.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]