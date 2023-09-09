package com.example.retail.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "db_sequence")
@Getter
public class DbSequence {
    @Id
    private String id;
    private Long seq;
}
