package com.rbc.nexgen.template.exception;
//https://www.baeldung.com/spring-boot-start
//https://www.baeldung.com/exception-handling-for-rest-with-spring
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RBCNexGenException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleRBCNexGenExceptions(
    	RBCNexGenException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getDetailDescription(), 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ IllegalAccessException.class, 
    	IllegalStateException.class, 
    	IllegalCallerException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequest(
      Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleNotFound(
      Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Rest Exception NOT FOUND", 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}