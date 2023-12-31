package com.example.retail.infrastructure.config;

import com.example.retail.domain.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bill.discounts.percentage")
@Setter
public class BillPercentageDiscountConfig {
    @NotBlank
    private Double employee;
    @NotBlank
    private Double affiliate;
    @NotBlank
    private Double loyalCustomer;
    @NotBlank
    private Integer customerLoyaltyPeriodInYears;
    private ProductCategory[] discountExcludedProducts;

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

    public ProductCategory[] getDiscountExcludedProducts() {
        return discountExcludedProducts;
    }
}
