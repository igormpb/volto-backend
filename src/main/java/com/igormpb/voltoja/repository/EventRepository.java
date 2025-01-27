package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.DriverEntity;
import com.igormpb.voltoja.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EventRepository extends MongoRepository<EventEntity, String> {
    @Query(value = "{name: { $regex: ?0, $options: 'i' }}")
    EventEntity findByName(String name);
}
