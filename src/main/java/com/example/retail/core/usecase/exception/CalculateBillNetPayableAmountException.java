package com.example.retail.core.usecase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CalculateBillNetPayableAmountException extends RuntimeException {
    private final Long billId;
}
