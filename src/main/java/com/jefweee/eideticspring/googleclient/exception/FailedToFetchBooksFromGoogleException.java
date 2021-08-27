package com.jefweee.eideticspring.googleclient.exception;

import org.springframework.beans.factory.annotation.Value;

public class FailedToFetchBooksFromGoogleException extends Throwable {

    @Value("${google.failedtofetchbooksexception.message:FailedToFetchBooksFromGoogle}")
    String message;

    public FailedToFetchBooksFromGoogleException() {
        super();
    }

    public FailedToFetchBooksFromGoogleException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
