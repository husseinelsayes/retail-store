package com.example.retail.usecase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BillNotFoundException extends RuntimeException {
    private final Long billId;
}
