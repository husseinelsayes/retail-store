package com.example.retail.usecase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class CalculateBillEligibleDiscountException extends RuntimeException {
    private final Long billId;
}
