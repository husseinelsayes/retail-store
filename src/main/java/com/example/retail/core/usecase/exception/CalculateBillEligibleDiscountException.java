package com.example.retail.core.usecase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class CalculateBillEligibleDiscountException extends RuntimeException {
    private Long billId;
}
