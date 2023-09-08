package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;

public interface CalculateBillEligibleDiscount {
    Double forBill(Bill bill);
}
