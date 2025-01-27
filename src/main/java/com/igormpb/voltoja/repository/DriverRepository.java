package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.BoardingEntity;
import com.igormpb.voltoja.entity.DriverEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DriverRepository extends MongoRepository<DriverEntity, String> {
    @Query(value = "{plate: { $regex: ?0, $options: 'i' }}")
    DriverEntity findByPlate(String plate);
}
