package com.example.retail.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BillNetPayableAmountResponse {
    private Long billId;
    private Double totalPayableAmount;
}
