package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventEntity, String> {
}
