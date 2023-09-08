package com.example.retail.core.domain;

import com.example.retail.core.usecase.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private final String productName;
    private final ProductCategoryEnum category;
    private final Double price;
}
