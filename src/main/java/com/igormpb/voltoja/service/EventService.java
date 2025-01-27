package com.igormpb.voltoja.service;

import com.igormpb.voltoja.entity.DriverEntity;
import com.igormpb.voltoja.entity.EventEntity;
import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.repository.EventRepository;
import com.igormpb.voltoja.request.PostEventRegisterRequest;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;


    public void Register(PostEventRegisterRequest body){
        try {
            var driver = EventEntity.builder()
                    .name(body.getName())
                    .type(body.getType())
                    .description(body.getDescription())
                    .address(body.getAddress())
                    .producerId(body.getProducerId())
                    .eventDate(body.getEventDate())
                    .startTime(body.getStartTime())
                    .endTime(body.getEndTime())
                    .bannerUrl(body.getBannerUrl())
                    .photoUrl(body.getPhotoUrl())
                    .createdAt(LocalDate.now().toString())
                    .updatedAt(LocalDate.now().toString());
            var result = eventRepository.findByName(body.getName());
            System.out.println("Resultado do findByName: " + result);
            if (result != null){
                throw new HandleErros("Evento já existe, por favor tente registrar outro", HttpStatus.BAD_REQUEST);
            };

            eventRepository.save(driver.build());
        }catch (Exception e){
            throw new HandleErros("Não foi possível registrar o evento, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
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
