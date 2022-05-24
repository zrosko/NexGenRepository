package com.rbc.nexgen.iipm.exception;
import java.lang.reflect.InvocationTargetException;

import org.apache.kafka.common.errors.SerializationException;
//https://www.baeldung.com/spring-boot-start
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ SerializationException.class })
    protected ResponseEntity<Object> handleNotFound(
    		SerializationException ex, WebRequest request) {
        return handleExceptionInternal(ex, "NOT FOUND (Kafka Serialization Error): " + ex.getMessage(), 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ InvocationTargetException.class })
    protected ResponseEntity<Object> handleKafkaTopicNotFound(
      Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Kafka Topic NOT FOUND", 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ IllegalAccessException.class, 
    	IllegalStateException.class, 
    	IllegalCallerException.class })
    public ResponseEntity<Object> handleBadRequest(
      Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}