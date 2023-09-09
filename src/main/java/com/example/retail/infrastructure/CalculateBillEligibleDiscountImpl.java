package com.example.retail.infrastructure;

import com.example.retail.domain.Bill;
import com.example.retail.usecase.CalculateBillEligibleDiscount;
import com.example.retail.usecase.CalculateBillFixedAmountDiscount;
import com.example.retail.usecase.CalculateBillPercentageDiscount;
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
