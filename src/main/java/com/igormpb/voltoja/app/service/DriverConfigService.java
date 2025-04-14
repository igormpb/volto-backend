package com.igormpb.voltoja.app.service;
import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/driver-config")
public class DriverConfigService {

    @Autowired
    BoardingRepository boardingRepository;

    @Autowired
    private MongoTemplate mongoRaw;

    public List<BoardingEntity> getList(String status) {
        Criteria criteria = Criteria.where("driver_id").is("");
        if (status != null) {
            criteria = criteria.and("status").ne(status);
        }

        Query query = new Query(criteria);
        return mongoRaw.find(query, BoardingEntity.class);
    }


    public List<BoardingEntity> GetById(String id) {
        Criteria criteria = Criteria.where("id").is(id).and("driver_id").is("");
        Query query = new Query(criteria);
        return mongoRaw.find(query, BoardingEntity.class);
    }
}
