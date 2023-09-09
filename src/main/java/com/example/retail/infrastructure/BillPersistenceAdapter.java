package com.example.retail.infrastructure;

import com.example.retail.domain.Bill;
import com.example.retail.usecase.GetBill;
import com.example.retail.usecase.exception.BillNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BillPersistenceAdapter implements GetBill {

    private final BillRepository billRepository;
    @Override
    public Bill byId(Long id) {
        return billRepository.findById(id).orElseThrow(()-> new BillNotFoundException(id));
    }
}
