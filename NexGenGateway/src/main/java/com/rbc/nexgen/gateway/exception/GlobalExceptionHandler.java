package com.rbc.nexgen.gateway.exception;
//https://www.baeldung.com/spring-boot-start
//https://www.baeldung.com/exception-handling-for-rest-with-spring
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RBCNexGenException.class })
    protected ResponseEntity<Object> handleRBCNexGenExceptions(
    	RBCNexGenException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getDetailDescription(), 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    protected ResponseEntity<Object> handleUserNameNotFound(
      Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "User name NOT FOUND", 
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
    
    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> handleNotFound(
      Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Rest Exception NOT FOUND", 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}