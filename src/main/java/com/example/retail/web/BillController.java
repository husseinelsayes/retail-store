package com.example.retail.web;

import com.example.retail.usecase.CalculateBillNetPayableAmount;
import com.example.retail.web.response.BillNetPayableAmountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    CalculateBillNetPayableAmount calculateBillNetPayableAmount;

    @GetMapping("/{billId}/net-payable-amount")
    public ResponseEntity<BillNetPayableAmountResponse> getNetPayableAmount(@PathVariable Long billId){
        Double payableAmount = calculateBillNetPayableAmount.forBill(billId);
        return ResponseEntity.ok(new BillNetPayableAmountResponse(billId, payableAmount));
    }
}
