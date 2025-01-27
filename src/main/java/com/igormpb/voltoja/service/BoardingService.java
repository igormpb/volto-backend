package com.igormpb.voltoja.service;

import com.igormpb.voltoja.entity.BoardingEntity;
import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.repository.BoardingRepository;
import com.igormpb.voltoja.request.PostBoardingRegisterRequest;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BoardingService {

    @Autowired
    BoardingRepository boardingRepository;

    public void Register(PostBoardingRegisterRequest body){
        try{
            var boarding = BoardingEntity.builder()
                    .address(body.getAddress())
                    .driverId(body.getDriverId())
                    .eventId(body.getEventId())
                    .price(body.getPrice());
            var result = boardingRepository.findByDriverId(body.getDriverId());
            System.out.println("Resultado do findById: " + result);
            if (!result.isEmpty()){
                throw new HandleErros("Motorista já esta em uma viagem, por favor tente registrar outro", HttpStatus.BAD_REQUEST);
            };

            boardingRepository.save(boarding.build());
        }catch (MongoException e) {
            throw new HandleErros("não foi possível criar sua viagem, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);

        }
    }
    public List<BoardingEntity> findAllByEventId(String eventId){
        try{
            return boardingRepository.findByEventId(eventId);
        } catch (Exception e) {
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
}
