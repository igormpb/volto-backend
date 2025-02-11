package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.entity.EventEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.infra.repository.EventRepository;
import com.igormpb.voltoja.domain.request.PostEventRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;


    public void Register(PostEventRegisterRequest body) {
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
                    .createdAt(LocalDate.now().toString())
                    .updatedAt(LocalDate.now().toString());
            var result = eventRepository.findByName(body.getName());
            System.out.println("Resultado do findByName: " + result);
            if (result != null) {
                throw new HandleErros("Evento já existe, por favor tente registrar outro", HttpStatus.BAD_REQUEST);
            }
            ;

            eventRepository.save(driver.build());
        } catch (Exception e) {
            throw new HandleErros("Não foi possível registrar o evento, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
    public void EditById(String id,PostEventRegisterRequest updatedEvent){
        try {
            var event = eventRepository.findById(id).get();


            // Atualizar os campos necessários
            if (updatedEvent.getName() != null) {
                event.setName(updatedEvent.getName());
            }
            if (updatedEvent.getType() != null) {
                event.setType(updatedEvent.getType());
            }
            if (updatedEvent.getDescription() != null) {
                event.setDescription(updatedEvent.getDescription());
            }
            if (updatedEvent.getAddress() != null) {
                event.setAddress(updatedEvent.getAddress());
            }
            if (updatedEvent.getProducerId() != null) {
                event.setProducerId(updatedEvent.getProducerId());
            }
            if (updatedEvent.getEventDate() != null) {
                event.setEventDate(updatedEvent.getEventDate());
            }
            if (updatedEvent.getStartTime() != null) {
                event.setStartTime(updatedEvent.getStartTime());
            }
            if (updatedEvent.getEndTime() != null) {
                event.setEndTime(updatedEvent.getEndTime());
            }
            if (updatedEvent.getBannerUrl() != null) {
                event.setBannerUrl(updatedEvent.getBannerUrl());
            }
            event.setUpdatedAt(LocalDate.now().toString());

            // Salvar o documento atualizado
            eventRepository.save(event);
        }catch (Exception e){
            throw new HandleErros("Não foi possivel editar o evento, tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<EventEntity> All() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public void Delete(String id) {
        try {
            eventRepository.deleteById(id);
        } catch (Exception e) {
            throw new HandleErros("Não foi possível deletar o evento,tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public List<EventEntity> AllByProducer(String id) {
        try {
            return eventRepository.findByProducerId(id);
        } catch (Exception e) {
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public EventEntity DetailById(String id) {
        try {
            var result = eventRepository.findById(id);

            if (result.isEmpty()) {
                throw new HandleErros("evento não encontrado.", HttpStatus.NOT_FOUND);

            }

            return result.get();

        } catch (Exception e) {
            throw new HandleErros("ocorreu um error ao tentar buscar o detalhes do evento, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

}
