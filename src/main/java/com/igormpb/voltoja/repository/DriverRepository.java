package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.DriverEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends MongoRepository<DriverEntity, String> {
    @Query(value = "{plate: { $regex: ?0, $options: 'i' }}")
    DriverEntity findByPlate(String plate);
}
