package com.example.retail.infrastructure.config;

import com.example.retail.core.usecase.ProductCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bill.discounts.percentage")
public class BillPercentageDiscountConfig {
    @NotBlank
    private Double employee;
    @NotBlank
    private Double affiliate;
    @NotBlank
    private Double loyalCustomer;
    @NotBlank
    private Integer customerLoyaltyPeriodInYears;
    private ProductCategoryEnum[] discountExcludedProducts;

    public Double forEmployee(){
        return this.employee;
    }

    public Double forAffiliate(){
        return this.affiliate;
    }

    public Double forLoyalCustomer(){
        return this.loyalCustomer;
    }

    public Integer getCustomerLoyaltyPeriodInYears() {
        return customerLoyaltyPeriodInYears;
    }

    public ProductCategoryEnum[] getDiscountExcludedProducts() {
        return discountExcludedProducts;
    }
}
