package com.example.retail.core.usecase;

import com.example.retail.core.usecase.BillNotFoundException;
import com.example.retail.core.usecase.CalculateBillNetPayableAmount;
import com.example.retail.core.usecase.GetBill;
import com.example.retail.infrastructure.CalculateBillNetPayableAmountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CalculateBillNetPayableAmountTest {
    private static final Long NON_EXISTING_BILL_ID = 87L;
    @Mock
    private GetBill getBill;
    private CalculateBillNetPayableAmount calculateBillNetPayableAmount;

    @BeforeEach
    public void setup(){
        calculateBillNetPayableAmount = new CalculateBillNetPayableAmountImpl(getBill);
    }
    @Test
    public void givenNotFoundBill_thenThrowException(){
        when(getBill.byId(NON_EXISTING_BILL_ID)).thenThrow(new BillNotFoundException(NON_EXISTING_BILL_ID));
        assertThrows(BillNotFoundException.class, ()->{
            calculateBillNetPayableAmount.forBill(NON_EXISTING_BILL_ID);
        });
        verify(getBill,times(1)).byId(NON_EXISTING_BILL_ID);
    }

}