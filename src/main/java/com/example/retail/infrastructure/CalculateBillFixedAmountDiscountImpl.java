package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillFixedAmountDiscount;
import com.example.retail.infrastructure.config.BillFixedDiscountConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CalculateBillFixedAmountDiscountImpl implements CalculateBillFixedAmountDiscount {
    private final BillFixedDiscountConfig billFixedDiscountConfig;
    @Override
    public Double forBill(Bill bill) {
        Double totalItemsAmount = bill.getTotalItemsAmount();
        Double aa = totalItemsAmount / billFixedDiscountConfig.getMoneyAmount();
        Integer numberOfFixedDiscounts = aa.intValue();
        Double totalFixedDiscount = numberOfFixedDiscounts * billFixedDiscountConfig.getDiscountPerFixedMoneyAmount();
        return totalFixedDiscount;
    }
}
