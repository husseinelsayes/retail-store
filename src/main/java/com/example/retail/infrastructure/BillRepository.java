package com.example.retail.infrastructure;

import com.example.retail.core.domain.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends MongoRepository<Bill, Long> {
}
