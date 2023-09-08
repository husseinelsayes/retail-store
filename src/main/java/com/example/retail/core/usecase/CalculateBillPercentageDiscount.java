package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;

public interface CalculateBillPercentageDiscount {
    public Double forBill(Bill bill);
}
