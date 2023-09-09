package com.example.retail.usecase;

import com.example.retail.domain.Bill;

public interface GetBill {
    Bill byId(Long id);
}
