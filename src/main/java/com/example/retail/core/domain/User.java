package com.example.retail.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private final Long id;
    private final String email;
    private final UserTypeEnum userType;
}
