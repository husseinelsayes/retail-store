package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;
import com.example.retail.infrastructure.CalculateBillFixedAmountDiscountImpl;
import com.example.retail.infrastructure.config.BillFixedDiscountConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CalculateBillFixedAmountDiscountTest {
    private static final Double DISCOUNT_ELIGIBLE_FIXED_AMOUNT = 100.00;
    private static final Double DISCOUNT_VALUE_FOR_EACH_FIXED_AMOUNT = 5.00;
    private static final Double TOTAL_BILL_AMOUNT = 990.00;
    @Mock
    private BillFixedDiscountConfig billFixedDiscountConfig;
    private CalculateBillFixedAmountDiscount calculateBillFixedAmountDiscount;

    @BeforeEach
    public void setup(){
        calculateBillFixedAmountDiscount = new CalculateBillFixedAmountDiscountImpl(billFixedDiscountConfig);
    }
    @Test
    public void givenExistingBill_thenApplyFixedDiscountOnEveryFixedAmount(){
        Bill bill = spy(existingBill());
        when(bill.getTotalItemsAmount()).thenReturn(TOTAL_BILL_AMOUNT);
        when(billFixedDiscountConfig.getMoneyAmount()).thenReturn(DISCOUNT_ELIGIBLE_FIXED_AMOUNT);
        when(billFixedDiscountConfig.getDiscountPerFixedMoneyAmount()).thenReturn(DISCOUNT_VALUE_FOR_EACH_FIXED_AMOUNT);
        Double calculatedFixedDiscount = calculateBillFixedAmountDiscount.forBill(bill);
        assertEquals(45.00,calculatedFixedDiscount);
    }

    private Bill existingBill(){
        return new Bill(null);
    }
}