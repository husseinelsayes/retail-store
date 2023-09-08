package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;
import com.example.retail.core.domain.User;
import com.example.retail.core.domain.UserTypeEnum;
import com.example.retail.infrastructure.config.BillPercentageDiscountConfig;
import com.example.retail.infrastructure.CalculateBillPercentageDiscountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CalculateBillPercentageDiscountTest {
    private static final Double BILL_TOTAL_ITEMS_AMOUNT = 100.00;
    private static final Double EMPLOYEE_PERCENT_DISCOUNT_AMOUNT = 30.00;
    @Mock
    private BillPercentageDiscountConfig billPercentageDiscountConfig;
    CalculateBillPercentageDiscount calculateBillPercentageDiscount;
    // if userType is employee percentage is 30%
    // if userType is affiliate percentage is 10%
    // is userType is 2 years customer percentage is 5%
    @BeforeEach
    public void setup(){
        calculateBillPercentageDiscount = new CalculateBillPercentageDiscountImpl(billPercentageDiscountConfig);
    }
    @Test
    public void givenEmployeeBill_thenCalculate30PercentDiscount(){
        Bill bill = spy(existingBill(mockUser(UserTypeEnum.EMPLOYEE)));
        when(bill.getTotalItemsAmount()).thenReturn(BILL_TOTAL_ITEMS_AMOUNT);
        when(billPercentageDiscountConfig.forEmployee()).thenReturn(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT);
        Double percentageDiscount = calculateBillPercentageDiscount.forBill(bill);
        assertEquals(EMPLOYEE_PERCENT_DISCOUNT_AMOUNT / 100 * BILL_TOTAL_ITEMS_AMOUNT,percentageDiscount);
    }

    private Bill existingBill(User issuedFor) {
        return new Bill(issuedFor);
    }

    private User mockUser(UserTypeEnum userType){
        User issuedFor = spy(new User(null,null, null));
        when(issuedFor.getUserType()).thenReturn(userType);
        return issuedFor;
    }
}