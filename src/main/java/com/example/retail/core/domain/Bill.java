package com.example.retail.core.domain;

import com.example.retail.core.usecase.ProductCategoryEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void addProduct(Product product){
        if(hasNoProductsList()) this.items = new ArrayList<>();
        this.items.add(product);
    }

    public Double getTotalItemsAmount(){
        return hasNoProductsList() ? 0.0 : items.stream().map(i -> i.getPrice()).collect(Collectors.summingDouble(Double::doubleValue));
    }
    public Double getTotalItemsAmountExcludingCategories(ProductCategoryEnum... excludedCategories){
        if(hasNoProductsList()) return  0.0;
        Double discountEligibleItemsTotalAmount = items.stream()
                .filter(i -> !Arrays.asList(excludedCategories).contains(i.getCategory()))
                .map(i -> i.getPrice()).
                collect(Collectors.summingDouble(Double::doubleValue));
        return discountEligibleItemsTotalAmount;
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

    private Boolean hasNoProductsList(){
        return items == null;
    }
}
