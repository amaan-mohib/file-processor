package com.example.fileprocessor.exception;

import com.example.fileprocessor.payload.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        log.error("e: ", ex);
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse body = new ErrorResponse("Validation failed", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(Exception exception) {
        // TODO send this stack trace to an observability tool
        log.error("e: ", exception);

        if (exception instanceof BadCredentialsException) {
            return new ResponseEntity<>(new ErrorResponse("The username or password is incorrect"), HttpStatus.BAD_REQUEST);
        }

        if (exception instanceof AccountStatusException) {
            return new ResponseEntity<>(new ErrorResponse("The account is locked"), HttpStatus.FORBIDDEN);
        }

        if (exception instanceof AccessDeniedException) {
            return new ResponseEntity<>(new ErrorResponse("You are not authorized to access this resource"), HttpStatus.FORBIDDEN);
        }

        if (exception instanceof SignatureException) {
            return new ResponseEntity<>(new ErrorResponse("The JWT signature is invalid"), HttpStatus.FORBIDDEN);
        }

        if (exception instanceof ExpiredJwtException) {
            return new ResponseEntity<>(new ErrorResponse("The JWT token has expired"), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", List.of("An unknown error has occurred")), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
