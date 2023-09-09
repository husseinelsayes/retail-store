package com.example.retail.core.usecase;


import com.example.retail.core.domain.Bill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetBillTest {
    @Autowired
    private GetBill getBill;

    @Test
    void givenExistingBill_shouldReturnBill(){
        Bill bill = getBill.byId(1L);
        Assertions.assertNotNull(bill);
    }
}