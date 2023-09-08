package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillEligibleDiscount;
import com.example.retail.core.usecase.CalculateBillPercentageDiscount;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateBillEligibleDiscountImpl implements CalculateBillEligibleDiscount {
    private final CalculateBillPercentageDiscount calculateBillPercentageDiscount;

    @Override
    public Double forBill(Bill bill) {
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        return null;
    }
}
