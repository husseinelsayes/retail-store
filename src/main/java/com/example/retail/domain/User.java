package com.example.retail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private final String fullName;
    private final String email;
    private final UserType userType;
    private final LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFullName(), user.getFullName()) && Objects.equals(getEmail(), user.getEmail()) && getUserType() == user.getUserType() && Objects.equals(getCreatedAt(), user.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getEmail(), getUserType(), getCreatedAt());
    }
}
