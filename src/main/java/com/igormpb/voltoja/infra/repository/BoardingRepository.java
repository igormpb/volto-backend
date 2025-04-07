package com.igormpb.voltoja.infra.repository;

import com.igormpb.voltoja.domain.entity.BoardingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface BoardingRepository extends MongoRepository<BoardingEntity, String> {
    @Query(value = "{event_id: ?0}")
    List<BoardingEntity> findByEventId(String eventId, Sort sort);
    @Query(value = "{driver_id: ?0}")
    List<BoardingEntity> findByDriverId(String driverId);
    @Query(value = "{ 'account_in_boarding': { $elemMatch: { 'account_id': ?0 } } }")
    List<BoardingEntity> findByAccountId(String accountId);

}
