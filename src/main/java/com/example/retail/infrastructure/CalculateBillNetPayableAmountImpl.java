package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillNetPayableAmount;
import com.example.retail.core.usecase.GetBill;
import org.springframework.stereotype.Component;

@Component
public class CalculateBillNetPayableAmountImpl implements CalculateBillNetPayableAmount {
    private final GetBill getBill;

    public CalculateBillNetPayableAmountImpl(GetBill getBill) {
        this.getBill = getBill;
    }

    @Override
    public Double forBill(Long billId) {
        Bill bill = getBill.byId(billId);
        return null;
    }
}
