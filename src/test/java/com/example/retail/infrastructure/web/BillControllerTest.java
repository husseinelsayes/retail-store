package com.example.retail.infrastructure.web;

import com.example.retail.usecase.exception.BillNotFoundException;
import com.example.retail.usecase.CalculateBillNetPayableAmount;
import com.example.retail.usecase.exception.CalculateBillNetPayableAmountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.retail.infrastructure.web.ControllerExceptionHandler.BILL_NOT_FOUND_MESSAGE_EN;
import static com.example.retail.infrastructure.web.ControllerExceptionHandler.BILL_PAYABLE_AMOUNT_CALCULATION_ERROR_MESSAGE_EN;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BillController.class)
class BillControllerTest {
    private static final Long EXISTING_BILL_ID = 100L;
    private static final Double TOTAL_BILL_PAYABLE_AMOUNT = 100.95;
    private static final Long NON_EXISTING_BILL_ID = 101L;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateBillNetPayableAmount calculateBillNetPayableAmount;
    @Test
    @WithMockUser("admin")
    void whenNotValidBillId_thenReturns400() throws Exception {
        mockMvc.perform(get("/api/bills/null/net-payable-amount")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser("admin")
    void whenExistingBill_thenCalculateNetPayableAmount() throws Exception {
        mockCalculatePayableAmount();
        mockMvc.perform(get(String.format("/api/bills/%s/net-payable-amount",EXISTING_BILL_ID))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.billId").value(EXISTING_BILL_ID))
                .andExpect(jsonPath("$.totalPayableAmount").value(TOTAL_BILL_PAYABLE_AMOUNT));
        verify(calculateBillNetPayableAmount,times(1)).forBill(EXISTING_BILL_ID);
    }

    @Test
    @WithMockUser("admin")
    void whenNonExistingBill_thenReturn400AndErrorMessage() throws Exception {
        mockNonExistingBill();
        mockMvc.perform(get(String.format("/api/bills/%s/net-payable-amount",NON_EXISTING_BILL_ID))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(String.format(BILL_NOT_FOUND_MESSAGE_EN, NON_EXISTING_BILL_ID)));
        verify(calculateBillNetPayableAmount,times(1)).forBill(NON_EXISTING_BILL_ID);
    }

    @Test
    @WithMockUser("admin")
    void whenCalculatePayableAmountException_thenReturn500AndErrorMessage() throws Exception {
        mockCalculationException();
        mockMvc.perform(get(String.format("/api/bills/%s/net-payable-amount",EXISTING_BILL_ID))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(String.format(BILL_PAYABLE_AMOUNT_CALCULATION_ERROR_MESSAGE_EN, EXISTING_BILL_ID)));
        verify(calculateBillNetPayableAmount,times(1)).forBill(EXISTING_BILL_ID);
    }

    @Test
    void whenNotAuthorized_thenReturn401() throws Exception {
        mockCalculatePayableAmount();
        mockMvc.perform(get(String.format("/api/bills/%s/net-payable-amount",EXISTING_BILL_ID))
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    private void mockCalculationException() {
        when(calculateBillNetPayableAmount.forBill(EXISTING_BILL_ID)).thenThrow(new CalculateBillNetPayableAmountException(EXISTING_BILL_ID));
    }

    private void mockNonExistingBill() {
        when(calculateBillNetPayableAmount.forBill(NON_EXISTING_BILL_ID)).thenThrow(new BillNotFoundException(NON_EXISTING_BILL_ID));
    }


    private void mockCalculatePayableAmount(){
        when(calculateBillNetPayableAmount.forBill(EXISTING_BILL_ID)).thenReturn(TOTAL_BILL_PAYABLE_AMOUNT);
    }

}
