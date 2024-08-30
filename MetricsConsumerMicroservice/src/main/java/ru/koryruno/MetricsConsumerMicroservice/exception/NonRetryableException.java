package ru.koryruno.MetricsConsumerMicroservice.exception;

public class NonRetryableException extends RuntimeException {

    public NonRetryableException() {}

    public NonRetryableException(String message) {
        super(message);
    }

}
