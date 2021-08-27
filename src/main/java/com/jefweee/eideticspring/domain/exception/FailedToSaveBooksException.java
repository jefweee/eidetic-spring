package com.jefweee.eideticspring.domain.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FailedToSaveBooksException extends RuntimeException {

    @Value("${failedtosavebooksexception.message:FailedToSaveBooks}")
    String message;

    public FailedToSaveBooksException() {
        super();
    }

    public FailedToSaveBooksException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
