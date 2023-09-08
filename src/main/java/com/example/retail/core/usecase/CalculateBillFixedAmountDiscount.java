package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;

public interface CalculateBillFixedAmountDiscount {
    Double forBill(Bill bill);
}
