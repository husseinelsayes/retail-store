package com.example.retail.infrastructure;

import com.example.retail.usecase.CalculateBillNetPayableAmount;
import org.springframework.stereotype.Component;

@Component
public class CalculateBillNetPayableAmountImpl implements CalculateBillNetPayableAmount {
    @Override
    public Double forBill(Long billId) {
        return null;
    }
}
