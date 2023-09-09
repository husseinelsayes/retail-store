package com.example.retail.infrastructure;

import com.example.retail.domain.Bill;
import com.example.retail.usecase.CalculateBillPercentageDiscount;
import com.example.retail.infrastructure.config.BillPercentageDiscountConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class CalculateBillPercentageDiscountImpl implements CalculateBillPercentageDiscount {
    private final BillPercentageDiscountConfig billPercentageDiscountConfig;
    private final Clock clock;
    @Override
    public Double forBill(Bill bill) {
        switch (bill.getIssuedFor().getUserType()){
            case EMPLOYEE : return getEmployeeDiscount(bill);
            case AFFILIATE : return getAffiliateDiscount(bill);
            case CUSTOMER : return getLoyalCustomerDiscount(bill);
            default: throw new RuntimeException("not discount eligible userType");
        }
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
        Long dd = numberOfYearsBetween(bill.getIssuedFor().getCreatedAt(), LocalDateTime.now(clock));
        if(numberOfYearsBetween(bill.getIssuedFor().getCreatedAt(), LocalDateTime.now(clock)) > billPercentageDiscountConfig.getCustomerLoyaltyPeriodInYears()){
            return billPercentageDiscountConfig.forLoyalCustomer() / 100 * bill.getTotalItemsAmountExcludingCategories(
                    billPercentageDiscountConfig.getDiscountExcludedProducts()
            );
        }else return 0.0;
    }

    private Long numberOfYearsBetween(LocalDateTime dateFrom, LocalDateTime dateTo){
        return ChronoUnit.YEARS.between(dateFrom, dateTo);
    }
}
