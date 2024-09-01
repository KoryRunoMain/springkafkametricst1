FROM openjdk:17-jdk-slim
COPY target/metrics-producer-microservice.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "/app/app.jar"]