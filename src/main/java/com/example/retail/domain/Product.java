package com.example.retail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Product {
    private final Long id;
    private final String productName;
    private final ProductCategory category;
    private final Double price;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getProductName(), product.getProductName()) && getCategory() == product.getCategory() && Objects.equals(getPrice(), product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getCategory(), getPrice());
    }

}
