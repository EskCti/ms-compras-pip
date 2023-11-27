package com.eskcti.workervalidator.services.exceptions;

public class LimitUnavailableException extends RuntimeException {

    public LimitUnavailableException(String message) {
        super(message);
    }
}
