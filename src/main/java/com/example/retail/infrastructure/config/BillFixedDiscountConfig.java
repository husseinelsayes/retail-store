package com.example.retail.infrastructure.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bill.discounts.fixed")
@Getter
public class BillFixedDiscountConfig {
    private Double moneyAmount;
    private Double discountPerFixedMoneyAmount;
}
