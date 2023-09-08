package com.example.retail.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private final Long id;
    private final String email;
    private final UserTypeEnum userType;
    private final LocalDateTime createdAt;
}
