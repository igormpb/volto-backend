package com.igormpb.voltoja.infra.repository;


import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.entity.CheckoutEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRepository extends MongoRepository<CheckoutEntity, String>{

    @Query(value = "{payment_id: ?0}")
    CheckoutEntity findByPaymentId(String paymentId);
}
