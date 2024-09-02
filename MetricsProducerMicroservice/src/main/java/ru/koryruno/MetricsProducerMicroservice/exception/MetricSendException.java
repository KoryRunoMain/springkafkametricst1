package ru.koryruno.MetricsProducerMicroservice.exception;

public class MetricSendException extends RuntimeException {

    public MetricSendException() {}

    public MetricSendException(String message) {
        super(message);
    }

}
