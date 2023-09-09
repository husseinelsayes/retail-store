package com.example.retail.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "user_seq";

    @Id
    private final Long id;
    private final String email;
    private final UserTypeEnum userType;
    private final LocalDateTime createdAt;
}
