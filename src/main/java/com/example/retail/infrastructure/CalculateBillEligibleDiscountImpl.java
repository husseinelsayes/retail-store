package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillEligibleDiscount;
import com.example.retail.core.usecase.CalculateBillFixedAmountDiscount;
import com.example.retail.core.usecase.CalculateBillPercentageDiscount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CalculateBillEligibleDiscountImpl implements CalculateBillEligibleDiscount {
    private final CalculateBillPercentageDiscount calculateBillPercentageDiscount;
    private final CalculateBillFixedAmountDiscount calculateBillFixedAmountDiscount;
    @Override
    public Double forBill(Bill bill) {
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        Double fixedAmountDiscount = calculateBillFixedAmountDiscount.forBill(bill);
        return percentageDiscount + fixedAmountDiscount;
    }
}
