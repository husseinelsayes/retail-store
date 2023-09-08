package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.usecase.exception.BillNotFoundException;
import com.example.retail.core.usecase.exception.CalculateBillEligibleDiscountException;
import com.example.retail.infrastructure.CalculateBillNetPayableAmountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CalculateBillNetPayableAmountTest {
    private static final Long NON_EXISTING_BILL_ID = 87L;
    private static final Long EXISTING_BILL_ID = 7L;
    private static final Double BILL_ITEMS_TOTAL_AMOUNT_BEFORE_DISCOUNT = 1500.79;
    private static final Double BILL_ELIGIBLE_DISCOUNT = 300.00;
    private static final Double BILL_NET_PAYABLE_AMOUNT = 1200.79;

    @Mock
    private GetBill getBill;
    @Mock
    private CalculateBillEligibleDiscount calculateBillEligibleDiscount;
    private CalculateBillNetPayableAmount calculateBillNetPayableAmount;

    @BeforeEach
    public void setup(){
        calculateBillNetPayableAmount = new CalculateBillNetPayableAmountImpl(getBill,calculateBillEligibleDiscount);
    }
    @Test
    public void givenNotFoundBill_thenThrowException(){
        when(getBill.byId(NON_EXISTING_BILL_ID)).thenThrow(new BillNotFoundException(NON_EXISTING_BILL_ID));
        assertThrows(BillNotFoundException.class, ()->{
            calculateBillNetPayableAmount.forBill(NON_EXISTING_BILL_ID);
        });
        verify(getBill,times(1)).byId(NON_EXISTING_BILL_ID);
    }

    @Test
    public void givenExistingBill_thenCalculateEligibleDiscounts(){
        when(getBill.byId(EXISTING_BILL_ID)).thenReturn(existingBill());
        calculateBillNetPayableAmount.forBill(EXISTING_BILL_ID);
        verify(calculateBillEligibleDiscount,times(1)).forBill(eq(existingBill()));
    }

    @Test
    public void givenCalculatedDiscount_thenApplyOnBillTotalAmount(){
        Bill bill = spy(existingBill());
        when(bill.getTotalItemsAmount()).thenReturn(BILL_ITEMS_TOTAL_AMOUNT_BEFORE_DISCOUNT);
        when(getBill.byId(EXISTING_BILL_ID)).thenReturn(bill);
        when(calculateBillEligibleDiscount.forBill(bill)).thenReturn(BILL_ELIGIBLE_DISCOUNT);
        Double netPayableAmount = calculateBillNetPayableAmount.forBill(EXISTING_BILL_ID);
        assertEquals(BILL_NET_PAYABLE_AMOUNT, netPayableAmount);
    }

    @Test
    public void whenCalculateDiscountException_thenThrowException(){
        when(calculateBillEligibleDiscount.forBill(any())).thenThrow(new CalculateBillEligibleDiscountException(EXISTING_BILL_ID));
        CalculateBillEligibleDiscountException exception = assertThrows(CalculateBillEligibleDiscountException.class, ()->{
            calculateBillNetPayableAmount.forBill(EXISTING_BILL_ID);
        });
        assertEquals(EXISTING_BILL_ID, exception.getBillId());
    }

    private Bill existingBill() {
        return new Bill(null);
    }

}