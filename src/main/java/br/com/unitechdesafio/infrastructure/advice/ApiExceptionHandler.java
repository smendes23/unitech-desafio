package br.com.unitechdesafio.infrastructure.advice;

import br.com.unitech.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<?> handleNegocio(ResourceNotFoundException ex, ServerWebExchange exchange) {

        var status = HttpStatus.NOT_FOUND;

        return handleExceptionInternal(ex, Collections.singletonMap("message", "resource not found"), new HttpHeaders(), status, exchange);
    }

    protected Mono<ResponseEntity<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, ServerWebExchange exchange) {

        return handleExceptionInternal(ex, Collections.singletonMap("message", "http message not readable because body is empty or invalid"), new HttpHeaders(), status, exchange);
    }

    protected Mono<ResponseEntity<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, ServerWebExchange exchange) {

        return handleExceptionInternal(ex,

                Collections.singletonMap("validations-error", ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList())),

                new HttpHeaders(), status, exchange);
    }
}