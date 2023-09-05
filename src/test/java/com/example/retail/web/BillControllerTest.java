package com.example.retail.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BillController.class)
public class BillControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenNotValidBillId_thenReturns400() throws Exception {
        mockMvc.perform(get("/api/bills/null/net-payable-amount")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}
