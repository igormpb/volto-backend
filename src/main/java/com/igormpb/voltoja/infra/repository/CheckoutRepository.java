package com.igormpb.voltoja.infra.repository;


import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.entity.CheckoutEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends MongoRepository<CheckoutEntity, String>{
}
