package com.example.retail.web;

import com.example.retail.usecase.CalculateBillNetPayableAmountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    public static final String BILL_NOT_FOUND_MESSAGE_EN = "Bill with id %s does not exist";

    @ExceptionHandler(CalculateBillNetPayableAmountException.class)
    public ResponseEntity<Object> handleCityNotFoundException(CalculateBillNetPayableAmountException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", String.format(BILL_NOT_FOUND_MESSAGE_EN, ex.getBillId()));
        return ResponseEntity.badRequest().body(body);
    }
}
