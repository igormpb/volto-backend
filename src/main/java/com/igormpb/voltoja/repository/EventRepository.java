package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<EventEntity, String> {
    @Query(value = "{name: { $regex: ?0, $options: 'i' }}")
    EventEntity findByName(String name);
    @Query(value = "{producer_id: { $regex: ?0, $options: 'i' }}")
    List<EventEntity> findByProducerId(String id);
}
