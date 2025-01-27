package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.BoardingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BoardingRepository extends MongoRepository<BoardingEntity, String> {
    @Query(value = "{event_id: ?0}")
    List<BoardingEntity> findByEventId(String eventId);
    @Query(value = "{driver_id: ?0}")
    List<BoardingEntity> findByDriverId(String driverId);

}
