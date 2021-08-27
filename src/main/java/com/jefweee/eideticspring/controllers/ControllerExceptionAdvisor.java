package com.jefweee.eideticspring.controllers;

import com.jefweee.eideticspring.domain.exception.FailedToSaveBooksException;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionAdvisor extends ResponseEntityExceptionHandler {

    @Value("${invalidparameterexception.message:InvalidParameter}")
    String invalidParameterMessage;

    @ExceptionHandler(FailedToSaveBooksException.class)
    public ResponseEntity<Object> handleFailedToSaveBooksException(
            FailedToSaveBooksException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToFetchBooksFromGoogleException.class)
    public ResponseEntity<Object> handleFailedToFetchBooksFromGoogleException(
            FailedToFetchBooksFromGoogleException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleFailedToFetchBooksFromGoogleException() {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", invalidParameterMessage);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
