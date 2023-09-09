package com.example.retail.usecase;

import com.example.retail.domain.*;
import com.example.retail.infrastructure.config.BillPercentageDiscountConfig;
import com.example.retail.infrastructure.CalculateBillPercentageDiscountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CalculateBillPercentageDiscountTest {
    private static final Double BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT = 100.00;
    private static final Double EMPLOYEE_PERCENT_DISCOUNT_AMOUNT = 30.00;
    private static final Double AFFILIATE_PERCENT_DISCOUNT_AMOUNT = 10.00;
    private static final Double LOYAL_CUSTOMER_PERCENT_DISCOUNT_AMOUNT = 5.00;
    private static final LocalDateTime BILL_ISSUED_DATE_DATE = LocalDateTime.of(2023,1,1,0,0);
    private static final LocalDateTime MORE_THAN_TWO_YEARS_CREATED_DATE = LocalDateTime.of(2020,1,1,0,0);
    private static final LocalDateTime LESS_THAN_TWO_YEARS_CREATED_DATE = LocalDateTime.of(2022,1,1,0,0);
    private static final Integer CUSTOMER_LOYALTY_THRESHOLD_IN_YEARS = 2;
    private static final Double NO_DISCOUNT_AMOUNT = 0.0;
    private static final ProductCategory[] NOT_ELIGIBLE_FOR_DISCOUNT_PRODUCTS = new ProductCategory[]{ProductCategory.GROCERY};
    @Mock
    private BillPercentageDiscountConfig billPercentageDiscountConfig;
    @Mock
    private Clock clock;
    CalculateBillPercentageDiscount calculateBillPercentageDiscount;

    @BeforeEach
    void setup(){
        calculateBillPercentageDiscount = new CalculateBillPercentageDiscountImpl(billPercentageDiscountConfig,clock);
    }
    @Test
    void givenEmployeeBill_thenCalculateEmployeePercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserType.EMPLOYEE,null)));
        when(bill.getTotalItemsAmountExcludingCategories(any())).thenReturn(BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forEmployee()).thenReturn(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    @Test
    void givenAffiliateBill_thenCalculateAffiliatePercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserType.AFFILIATE,null)));
        when(bill.getTotalItemsAmountExcludingCategories(any())).thenReturn(BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forAffiliate()).thenReturn(AFFILIATE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(AFFILIATE_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    @Test
    void givenBillForPassingLoyaltyPeriodCustomer_thenCalculateLoyalCustomerPercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserType.CUSTOMER,MORE_THAN_TWO_YEARS_CREATED_DATE)));
        when(clock.instant()).thenReturn(BILL_ISSUED_DATE_DATE.toInstant(ZoneOffset.UTC));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(bill.getTotalItemsAmountExcludingCategories(any())).thenReturn(BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forLoyalCustomer()).thenReturn(LOYAL_CUSTOMER_PERCENT_DISCOUNT_AMOUNT);
        when(billPercentageDiscountConfig.getCustomerLoyaltyPeriodInYears()).thenReturn(CUSTOMER_LOYALTY_THRESHOLD_IN_YEARS);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(LOYAL_CUSTOMER_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    @Test
    void givenBillForNotPassingLoyaltyPeriodCustomer_thenNoPercentDiscountApplied(){
        Bill bill = existingBill(mockUser(UserType.CUSTOMER,LESS_THAN_TWO_YEARS_CREATED_DATE));
        when(clock.instant()).thenReturn(BILL_ISSUED_DATE_DATE.toInstant(ZoneOffset.UTC));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(billPercentageDiscountConfig.getCustomerLoyaltyPeriodInYears()).thenReturn(CUSTOMER_LOYALTY_THRESHOLD_IN_YEARS);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(NO_DISCOUNT_AMOUNT,percentageDiscount);
    }

    @Test
    void givenExistingBill_thenApplyDiscountOnAllowedProductsOnly(){
        Bill bill = mixedProductsBill();
        when(billPercentageDiscountConfig.getDiscountExcludedProducts()).thenReturn(new ProductCategory[]{ProductCategory.GROCERY});
        when(billPercentageDiscountConfig.forEmployee()).thenReturn(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(450.00,percentageDiscount);
    }

    @Test
    void givenNotDiscountEligibleUserType_whenCalculatePercentageDiscount_thenThrowException(){
        Bill bill = existingBill(mockUser(UserType.OTHER,LESS_THAN_TWO_YEARS_CREATED_DATE));
        assertThrows(RuntimeException.class,()-> calculateBillPercentageDiscount.forBill(bill));
    }


    private Bill existingBill(User issuedFor) {
        return new Bill( issuedFor, BILL_ISSUED_DATE_DATE);
    }

    private User mockUser(UserType userType, LocalDateTime createdAt){
        User issuedFor = spy(new User(null,null,null, null,createdAt));
        when(issuedFor.getUserType()).thenReturn(userType);
        return issuedFor;
    }

    private Bill mixedProductsBill(){
        Bill bill = new Bill( mockUser(UserType.EMPLOYEE,null), null);
        bill.addItem(new Product(1L,"mobile phone", ProductCategory.ELECTRONIC_DEVICE, 1000.00));
        bill.addItem(new Product(2L,"cucumber", ProductCategory.GROCERY, 5.00));
        bill.addItem(new Product(3L,"playstation", ProductCategory.ELECTRONIC_DEVICE, 500.00));
        return bill;
    }
}