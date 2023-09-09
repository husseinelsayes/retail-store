package com.example.retail.infrastructure;

import com.example.retail.domain.Bill;
import com.example.retail.usecase.CalculateBillEligibleDiscount;
import com.example.retail.usecase.CalculateBillNetPayableAmount;
import com.example.retail.usecase.GetBill;
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
        return bill.getTotalItemsAmount() - discount;
    }
}
