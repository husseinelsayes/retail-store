package com.example.retail.usecase;

import com.example.retail.domain.Bill;

public interface CalculateBillEligibleDiscount {
    Double forBill(Bill bill);
}
