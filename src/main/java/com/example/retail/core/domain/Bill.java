package com.example.retail.core.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bill {
    Long id;
    User issuedFor;
    LocalDateTime issuedAt;
    List<Product> items;

    public Bill(User issuedFor){
        this.issuedFor = issuedFor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill bill)) return false;
        return Objects.equals(id, bill.id) && Objects.equals(issuedFor, bill.issuedFor) && Objects.equals(issuedAt, bill.issuedAt) && Objects.equals(items, bill.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issuedFor, issuedAt, items);
    }
}
