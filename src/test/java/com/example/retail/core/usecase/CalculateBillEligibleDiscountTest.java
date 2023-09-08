package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;
import com.example.retail.infrastructure.CalculateBillEligibleDiscountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class CalculateBillEligibleDiscountTest {
    // should calculate percentage discount
    // should calculate fixed amount discount
    // should aggregate all eligible discounts for a bill
    @Mock
    private CalculateBillPercentageDiscount calculateBillPercentageDiscount;
    private CalculateBillEligibleDiscount calculateBillEligibleDiscount;

    @BeforeEach
    public void setup(){
        calculateBillEligibleDiscount = new CalculateBillEligibleDiscountImpl(calculateBillPercentageDiscount);
    }
    @Test
    public void givenExistingBill_thenCalculatePercentageDiscount(){
        Bill bill = existingBill();
        calculateBillEligibleDiscount.forBill(bill);
        verify(calculateBillPercentageDiscount,times(1)).forBill(eq(bill));
    }

    private Bill existingBill() {
        return new Bill(null);
    }
}