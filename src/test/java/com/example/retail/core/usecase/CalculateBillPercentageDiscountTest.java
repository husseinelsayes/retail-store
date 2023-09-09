package com.example.retail.core.usecase;

import com.example.retail.core.domain.*;
import com.example.retail.infrastructure.config.BillPercentageDiscountConfig;
import com.example.retail.infrastructure.CalculateBillPercentageDiscountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CalculateBillPercentageDiscountTest {
    private static final Double BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT = 100.00;
    private static final Double EMPLOYEE_PERCENT_DISCOUNT_AMOUNT = 30.00;
    private static final Double AFFILIATE_PERCENT_DISCOUNT_AMOUNT = 10.00;
    private static final Double LOYAL_CUSTOMER_PERCENT_DISCOUNT_AMOUNT = 5.00;
    private static final LocalDateTime MORE_THAN_TWO_YEARS_CREATED_DATE = LocalDateTime.of(2020,1,1,0,0);
    private static final LocalDateTime LESS_THAN_TWO_YEARS_CREATED_DATE = LocalDateTime.of(2022,1,1,0,0);
    private static final Integer CUSTOMER_LOYALTY_THRESHOLD_IN_YEARS = 2;
    private static final Double NO_DISCOUNT_AMOUNT = 0.0;
    private static final ProductCategoryEnum[] NOT_ELIGIBLE_FOR_DISCOUNT_PRODUCTS = new ProductCategoryEnum[]{ProductCategoryEnum.GROCERY};
    @Mock
    private BillPercentageDiscountConfig billPercentageDiscountConfig;
    CalculateBillPercentageDiscount calculateBillPercentageDiscount;

    @BeforeEach
    public void setup(){
        calculateBillPercentageDiscount = new CalculateBillPercentageDiscountImpl(billPercentageDiscountConfig);
    }
    @Test
    public void givenEmployeeBill_thenCalculateEmployeePercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserTypeEnum.EMPLOYEE,null)));
        when(bill.getTotalItemsAmountExcludingCategories(any())).thenReturn(BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forEmployee()).thenReturn(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    @Test
    public void givenAffiliateBill_thenCalculateAffiliatePercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserTypeEnum.AFFILIATE,null)));
        when(bill.getTotalItemsAmountExcludingCategories(any())).thenReturn(BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forAffiliate()).thenReturn(AFFILIATE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(AFFILIATE_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    @Test
    public void givenBillForPassingLoyaltyPeriodCustomer_thenCalculateLoyalCustomerPercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserTypeEnum.CUSTOMER,MORE_THAN_TWO_YEARS_CREATED_DATE)));
        when(bill.getTotalItemsAmountExcludingCategories(any())).thenReturn(BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forLoyalCustomer()).thenReturn(LOYAL_CUSTOMER_PERCENT_DISCOUNT_AMOUNT);
        when(billPercentageDiscountConfig.getCustomerLoyaltyPeriodInYears()).thenReturn(CUSTOMER_LOYALTY_THRESHOLD_IN_YEARS);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(LOYAL_CUSTOMER_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_DISCOUNT_ELIGIBLE_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    @Test
    public void givenBillForNotPassingLoyaltyPeriodCustomer_thenNoPercentDiscountApplied(){
        Bill bill = existingBill(mockUser(UserTypeEnum.CUSTOMER,LESS_THAN_TWO_YEARS_CREATED_DATE));
        when(billPercentageDiscountConfig.getCustomerLoyaltyPeriodInYears()).thenReturn(CUSTOMER_LOYALTY_THRESHOLD_IN_YEARS);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(NO_DISCOUNT_AMOUNT,percentageDiscount);
    }

    @Test
    public void givenExistingBill_thenApplyDiscountOnAllowedProductsOnly(){
        Bill bill = mixedProductsBill();
        when(billPercentageDiscountConfig.getDiscountExcludedProducts()).thenReturn(new ProductCategoryEnum[]{ProductCategoryEnum.GROCERY});
        when(billPercentageDiscountConfig.forEmployee()).thenReturn(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(450.00,percentageDiscount);
    }

    private Bill existingBill(User issuedFor) {
        return new Bill(issuedFor);
    }

    private User mockUser(UserTypeEnum userType, LocalDateTime createdAt){
        User issuedFor = spy(new User(null,null, null,createdAt));
        when(issuedFor.getUserType()).thenReturn(userType);
        return issuedFor;
    }

    private Bill mixedProductsBill(){
        Bill bill = new Bill(mockUser(UserTypeEnum.EMPLOYEE,null));
        bill.addProduct(new Product("mobile phone", ProductCategoryEnum.ELECTRONIC_DEVICE, 1000.00));
        bill.addProduct(new Product("cucumber", ProductCategoryEnum.GROCERY, 5.00));
        bill.addProduct(new Product("playstation", ProductCategoryEnum.ELECTRONIC_DEVICE, 500.00));
        return bill;
    }
}