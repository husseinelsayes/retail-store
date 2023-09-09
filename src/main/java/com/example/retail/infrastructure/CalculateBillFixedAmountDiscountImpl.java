package com.example.retail.infrastructure;

import com.example.retail.domain.Bill;
import com.example.retail.usecase.CalculateBillFixedAmountDiscount;
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
        Double fixedAmountOccurrencesCount = totalItemsAmount / billFixedDiscountConfig.getMoneyAmount();
        Integer numberOfFixedDiscounts = fixedAmountOccurrencesCount.intValue();
        return numberOfFixedDiscounts * billFixedDiscountConfig.getDiscountPerFixedMoneyAmount();
    }
}
