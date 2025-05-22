package com.igormpb.voltoja.app.service;
import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.entity.EventEntity;
import com.igormpb.voltoja.domain.response.GetBordingWithEventResponse;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.infra.repository.EventRepository;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DriverConfigService {

    @Autowired
    BoardingRepository boardingRepository;
    @Autowired
    EventRepository eventRepository;

    @Autowired
    private MongoTemplate mongoRaw;

    public List<GetBordingWithEventResponse> getList(String status, String id) {
        List<GetBordingWithEventResponse> response = new ArrayList<>();
        Criteria criteria = Criteria.where("driver_id").is(id);
        if (status != null && !status.isEmpty() && !"null".equalsIgnoreCase(status)) {
            criteria = criteria.and("status").ne(status);
        }

        Query query = new Query(criteria);
        List<BoardingEntity> boardings = mongoRaw.find(query, BoardingEntity.class);
        boardings.forEach((boarding) -> {

            EventEntity event = eventRepository.findById(boarding.getEventId()).get();
            response.add(new GetBordingWithEventResponse(boarding,event));

        });



        return response;
    }


    public List<BoardingEntity> GetById(String id) {
        Criteria criteria = Criteria.where("id").is(id).and("driver_id").is("");

        Query query = new Query(criteria);
        return mongoRaw.find(query, BoardingEntity.class);
    }
}
