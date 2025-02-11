package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.domain.request.PostBoardingRegisterRequest;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BoardingService {

    @Autowired
    BoardingRepository boardingRepository;

    public void Register(PostBoardingRegisterRequest body) {
        try {
            var boarding = BoardingEntity.builder()
                    .address(body.getAddress())
                    .driverId(body.getDriverId())
                    .eventId(body.getEventId())
                    .price(body.getPrice())
                    .timeToGo(body.getTimeGo())
                    .timeToOut(body.getTimeOut());
            var result = boardingRepository.findByDriverId(body.getDriverId());
            System.out.println("Resultado do findById: " + result);
            if (!result.isEmpty()) {
                throw new HandleErros("Motorista já esta em uma viagem, por favor tente registrar outro", HttpStatus.BAD_REQUEST);
            }
            ;

            boardingRepository.save(boarding.build());
        } catch (MongoException e) {
            throw new HandleErros("não foi possível criar sua viagem, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);

        }
    }

    public void EditById(String id, PostBoardingRegisterRequest updatedBoarding){
        try {
            var boarding = boardingRepository.findById(id).get();


            // Atualizar os campos necessários
            if (updatedBoarding.getAddress() != null) {
                boarding.setAddress(updatedBoarding.getAddress());
            }
            if (updatedBoarding.getDriverId() != null) {
                boarding.setDriverId(updatedBoarding.getDriverId());
            }
            if (updatedBoarding.getEventId() != null) {
                boarding.setEventId(updatedBoarding.getEventId());
            }
            if (updatedBoarding.getPrice() != null) {
                boarding.setPrice(updatedBoarding.getPrice());
            }
            if (updatedBoarding.getTimeGo() != null) {
                boarding.setTimeToGo(updatedBoarding.getTimeGo());
            }
            if (updatedBoarding.getTimeOut() != null) {
                boarding.setTimeToOut(updatedBoarding.getTimeOut());
            }


            // Salvar o documento atualizado
            boardingRepository.save(boarding);
        }catch (Exception e){
            throw new HandleErros("Não foi possivel editar a viagem, tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<BoardingEntity> findAllByEventId(String eventId) {
        try {
            return boardingRepository.findByEventId(eventId);
        } catch (Exception e) {
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public void DeleteById(String id) {
        try {
            boardingRepository.deleteById(id);
        } catch (Exception e) {
            throw new HandleErros("Não foi possível deletar a viagem, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
}
