package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.CalculateBillPercentageDiscount;
import com.example.retail.infrastructure.config.BillPercentageDiscountConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class CalculateBillPercentageDiscountImpl implements CalculateBillPercentageDiscount {
    private final BillPercentageDiscountConfig billPercentageDiscountConfig;
    @Override
    public Double forBill(Bill bill) {
        switch (bill.getIssuedFor().getUserType()){
            case EMPLOYEE : return getEmployeeDiscount(bill);
            case AFFILIATE : return getAffiliateDiscount(bill);
            case CUSTOMER : return getLoyalCustomerDiscount(bill);
        }
        return null;
    }

    private Double getEmployeeDiscount(Bill bill){
        return billPercentageDiscountConfig.forEmployee() / 100 *
                bill.getTotalItemsAmountExcludingCategories(
                        billPercentageDiscountConfig.getDiscountExcludedProducts()
                );
    }

    private Double getAffiliateDiscount(Bill bill){
        return billPercentageDiscountConfig.forAffiliate() / 100 *
                bill.getTotalItemsAmountExcludingCategories(
                        billPercentageDiscountConfig.getDiscountExcludedProducts()
                );
    }

    private Double getLoyalCustomerDiscount(Bill bill){
        Double customerDiscount = billPercentageDiscountConfig.forLoyalCustomer();
        if(numberOfYearsBetween(bill.getIssuedFor().getCreatedAt(), LocalDateTime.now()) > billPercentageDiscountConfig.getCustomerLoyaltyPeriodInYears()){
            return billPercentageDiscountConfig.forLoyalCustomer() / 100 * bill.getTotalItemsAmountExcludingCategories(
                    billPercentageDiscountConfig.getDiscountExcludedProducts()
            );
        }else return 0.0;
    }

    private Long numberOfYearsBetween(LocalDateTime dateFrom, LocalDateTime dateTo){
        return ChronoUnit.YEARS.between(dateFrom, dateTo);
    }
}
