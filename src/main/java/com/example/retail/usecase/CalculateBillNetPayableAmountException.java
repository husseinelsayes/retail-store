package com.example.retail.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CalculateBillNetPayableAmountException extends RuntimeException {
    private Long billId;
}
