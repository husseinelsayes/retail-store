package com.example.retail.infrastructure;

import com.example.retail.domain.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends MongoRepository<Bill, Long> {
}
