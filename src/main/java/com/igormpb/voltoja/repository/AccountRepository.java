package com.igormpb.voltoja.repository;

import com.igormpb.voltoja.entity.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {

    @Query(value = "{email: ?0}")
    AccountEntity findByEmail(String email);

}
