package com.example.retail.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @GetMapping("/{billId}/net-payable-amount")
    public ResponseEntity getNetPayableAmount(@PathVariable Long billId){
        return null;
    }
}
