package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillPercentageDiscount;
import com.example.retail.infrastructure.config.BillPercentageDiscountConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CalculateBillPercentageDiscountImpl implements CalculateBillPercentageDiscount {
    private final BillPercentageDiscountConfig billPercentageDiscountConfig;
    @Override
    public Double forBill(Bill bill) {
        switch (bill.getIssuedFor().getUserType()){
            case EMPLOYEE : return getEmployeeDiscount(bill);
            case AFFILIATE : return getAffiliateDiscount(bill);
        }
        return null;
    }

    private Double getEmployeeDiscount(Bill bill){
        return billPercentageDiscountConfig.forEmployee() / 100 * bill.getTotalItemsAmount();
    }

    private Double getAffiliateDiscount(Bill bill){
        return billPercentageDiscountConfig.forAffiliate() / 100 * bill.getTotalItemsAmount();
    }
}
