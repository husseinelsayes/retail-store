package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;
import com.example.retail.infrastructure.CalculateBillEligibleDiscountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateBillEligibleDiscountTest {
    private static final Double PERCENTAGE_DISCOUNT_AMOUNT = 20.00;
    private static final Double FIXED_DISCOUNT_AMOUNT = 10.00;

    @Mock
    private CalculateBillPercentageDiscount calculateBillPercentageDiscount;
    @Mock
    private CalculateBillFixedAmountDiscount calculateBillFixedAmountDiscount;
    private CalculateBillEligibleDiscount calculateBillEligibleDiscount;

    @BeforeEach
    void setup(){
        calculateBillEligibleDiscount = new CalculateBillEligibleDiscountImpl(calculateBillPercentageDiscount, calculateBillFixedAmountDiscount);
    }
    @Test
    void givenExistingBill_thenCalculatePercentageDiscount(){
        Bill bill = existingBill();
        calculateBillEligibleDiscount.forBill(bill);
        verify(calculateBillPercentageDiscount,times(1)).forBill(bill);
    }

    @Test
    void givenExistingBill_thenCalculateFixedAmountDiscount(){
        Bill bill = existingBill();
        calculateBillEligibleDiscount.forBill(bill);
        verify(calculateBillFixedAmountDiscount,times(1)).forBill(bill);
    }

    @Test
    void givenExistingBill_thenCalculateAllDiscountTypes(){
        Bill bill = existingBill();
        when(calculateBillPercentageDiscount.forBill(bill)).thenReturn(PERCENTAGE_DISCOUNT_AMOUNT);
        when(calculateBillFixedAmountDiscount.forBill(bill)).thenReturn(FIXED_DISCOUNT_AMOUNT);
        Double allEligibleDiscounts = calculateBillEligibleDiscount.forBill(bill);
        assertEquals(PERCENTAGE_DISCOUNT_AMOUNT+FIXED_DISCOUNT_AMOUNT,allEligibleDiscounts);
    }

    private Bill existingBill() {
        return new Bill(null);
    }
}