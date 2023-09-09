package com.example.retail.usecase;

import com.example.retail.domain.Bill;

public interface CalculateBillFixedAmountDiscount {
    Double forBill(Bill bill);
}
