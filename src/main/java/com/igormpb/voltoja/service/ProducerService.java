package com.igormpb.voltoja.service;

import com.igormpb.voltoja.entity.ProducerEntity;
import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.repository.ProducerRepository;
import com.igormpb.voltoja.request.PostProducerRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProducerService {
    @Autowired
    ProducerRepository producerRepository;

    public void Register(PostProducerRegisterRequest body){
        try {
            var produce = ProducerEntity.builder()
                    .name(body.getName())
                    .photoUrl(body.getPhotoUrl())
                    .createdAt(LocalDate.now().toString())
                    .updatedAt(LocalDate.now().toString());
            var result = producerRepository.findByName(body.getName());
            if (result != null){
                throw new HandleErros("Produtor ja cadastrado, tente cadastrar outro", HttpStatus.BAD_REQUEST);
            }
            producerRepository.save(produce.build());
        }catch (Exception e){
            throw new HandleErros("Não foi possivel cadastrar o produtor, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
}
