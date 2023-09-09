package com.example.retail.usecase;

import com.example.retail.domain.Bill;

public interface CalculateBillPercentageDiscount {
    public Double forBill(Bill bill);
}
