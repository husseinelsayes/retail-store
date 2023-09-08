package com.example.retail.core.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BillNotFoundException extends RuntimeException {
    private Long billId;
}
