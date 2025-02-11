package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.entity.ProducerEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.infra.repository.ProducerRepository;
import com.igormpb.voltoja.domain.request.PostProducerRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProducerService {
    @Autowired
    ProducerRepository producerRepository;

    public void Register(PostProducerRegisterRequest body) {
        try {
            var produce = ProducerEntity.builder()
                    .name(body.getName())
                    .photoUrl(body.getPhotoUrl())
                    .createdAt(LocalDate.now().toString())
                    .updatedAt(LocalDate.now().toString());
            var result = producerRepository.findByName(body.getName());
            if (result != null) {
                throw new HandleErros("Produtor ja cadastrado, tente cadastrar outro", HttpStatus.BAD_REQUEST);
            }
            producerRepository.save(produce.build());
        } catch (Exception e) {
            throw new HandleErros("Não foi possivel cadastrar o produtor, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public void EditById(String id, PostProducerRegisterRequest updatedProducer){
        try {
            var producer = producerRepository.findById(id).get();


            // Atualizar os campos necessários
            if (updatedProducer.getName() != null) {
                producer.setName(updatedProducer.getName());
            }
            if (updatedProducer.getPhotoUrl() != null) {
                producer.setPhotoUrl(updatedProducer.getPhotoUrl());
            }
            producer.setUpdatedAt(LocalDate.now().toString());

            // Salvar o documento atualizado
            producerRepository.save(producer);
        }catch (Exception e){
            throw new HandleErros("Não foi possivel editar o evento, tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ProducerEntity> All() {
        try {
            return producerRepository.findAll();
        } catch (Exception e) {
            throw new HandleErros("Não foi possível listar os produtores, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public ProducerEntity findById(String id) {
        try {
            var produce = producerRepository.findById(id);
            if (produce.isEmpty()) {
                throw new HandleErros("Produtor não encontrado", HttpStatus.NOT_FOUND);
            }
            return produce.get();
        } catch (Exception e) {
            throw new HandleErros("Não foi possível encontrar o produtor, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteById(String id) {
        try {
            producerRepository.deleteById(id);
        } catch (Exception e) {
            throw new HandleErros("Não foi possível deletar o produtor, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
}
