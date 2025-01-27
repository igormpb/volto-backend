package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.ProducerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProducerRepository extends MongoRepository<ProducerEntity, String> {
    @Query(value = "{name: ?0}")
    ProducerEntity findByName(String name);
}
