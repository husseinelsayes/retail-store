package com.example.retail.core.usecase;

import com.example.retail.core.domain.Bill;

public interface GetBill {
    Bill byId(Long id);
}
