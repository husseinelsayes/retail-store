package com.example.retail.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    User issuedFor;
    LocalDateTime issuedAt;
    List<Product> items;
}
