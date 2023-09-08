package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillEligibleDiscount;
import com.example.retail.core.usecase.CalculateBillNetPayableAmount;
import com.example.retail.core.usecase.GetBill;
import org.springframework.stereotype.Component;

@Component
public class CalculateBillNetPayableAmountImpl implements CalculateBillNetPayableAmount {
    private final GetBill getBill;
    private final CalculateBillEligibleDiscount calculateBillEligibleDiscount;

    public CalculateBillNetPayableAmountImpl(GetBill getBill, CalculateBillEligibleDiscount calculateBillEligibleDiscount) {
        this.getBill = getBill;
        this.calculateBillEligibleDiscount = calculateBillEligibleDiscount;
    }

    @Override
    public Double forBill(Long billId) {
        Bill bill = getBill.byId(billId);
        Double discount = calculateBillEligibleDiscount.forBill(bill);
        return null;
    }
}
