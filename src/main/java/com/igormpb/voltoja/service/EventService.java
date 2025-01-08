package com.igormpb.voltoja.service;

import com.igormpb.voltoja.entity.EventEntity;
import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.repository.EventRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;



    public List<EventEntity> All(){
        try{
            return eventRepository.findAll();
        } catch (Exception e) {
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public EventEntity DetailById(String id){
        try{
            var result = eventRepository.findById(id);

            if (result.isEmpty()){
                throw new HandleErros("evento não encontrado.", HttpStatus.NOT_FOUND);

            }

            return result.get();

        } catch (Exception e) {
            throw new HandleErros("ocorreu um error ao tentar buscar o detalhes do evento, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

}
