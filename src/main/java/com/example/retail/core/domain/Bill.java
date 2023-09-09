package com.example.retail.core.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Getter
@Document(collection = "bills")
public class Bill {
    @Transient
    public static final String SEQUENCE_NAME = "bill_seq";

    @Id
    BigInteger _id;
    User issuedFor;
    LocalDateTime issuedAt;
    List<Product> items;

    public Bill(User issuedFor){
        this.issuedFor = issuedFor;
    }

    public void addProduct(Product product){
        if(items == null) this.items = new ArrayList<>();
        this.items.add(product);
    }

    public Double getTotalItemsAmount(){
        return items == null ? 0.0 : items.stream().map(i -> i.getPrice()).collect(Collectors.summingDouble(Double::doubleValue));
    }
    public Double getTotalItemsAmountExcludingCategories(ProductCategoryEnum... excludedCategories){
        if(items == null) return  0.0;
        return items.stream()
                .filter(i -> !Arrays.asList(excludedCategories).contains(i.getCategory()))
                .map(i -> i.getPrice()).
                collect(Collectors.summingDouble(Double::doubleValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill bill)) return false;
        return Objects.equals(_id, bill._id) && Objects.equals(issuedFor, bill.issuedFor) && Objects.equals(issuedAt, bill.issuedAt) && Objects.equals(items, bill.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, issuedFor, issuedAt, items);
    }

}
