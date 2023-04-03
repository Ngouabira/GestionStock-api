package com.gildas.gestionstock.handler;

import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(EntityNotFoundException exception, WebRequest webRequest){

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDTO errorDTO= ErrorDTO.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, notFound);
    }


    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorDTO> handleInvalidOperationException(EntityNotFoundException exception, WebRequest webRequest){

        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorDTO errorDTO= ErrorDTO.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, notFound);
    }
}
