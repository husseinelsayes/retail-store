package com.example.retail.core.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Getter
public class Bill {
    Long id;
    User issuedFor;
    LocalDateTime issuedAt;
    List<Product> items;

    public Bill(User issuedFor){
        this.issuedFor = issuedFor;
    }

    public Double getTotalItemsAmount(){
        return items == null || items.size() == 0 ? 0.0 : items.stream().map(i -> i.getPrice()).collect(Collectors.summingDouble(Double::doubleValue));
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
