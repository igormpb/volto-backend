package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.entity.DriverEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostEventFilterRequest;
import com.igormpb.voltoja.domain.response.PostBoardingResponse;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.domain.request.PostBoardingRegisterRequest;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;



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

    public List<BoardingEntity> findByAccountId(String accountId) {
        try {
            return boardingRepository.findByAccountId(accountId);
        } catch (Exception e) {
            throw new HandleErros("não foi listar as viagens, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public List<PostBoardingResponse> findAllByEventId(String eventId, PostEventFilterRequest body) {
        try {
            var boardings = boardingRepository.findByEventId(eventId);
            if (body.getPrice() != null && !body.getPrice().isEmpty()){
                if (body.getPrice().equalsIgnoreCase("low")) {
                    boardings = boardings.stream()
                            .sorted(Comparator.comparing(BoardingEntity::getPrice))
                            .collect(Collectors.toList());
                } else if (body.getPrice().equalsIgnoreCase("high")) {
                    boardings = boardings.stream()
                            .sorted(Comparator.comparing(BoardingEntity::getPrice).reversed())
                            .collect(Collectors.toList());
                }
            }
            if (body.getLocation() != null && !body.getLocation().isEmpty()){
                boardings = boardings.stream()
                        .filter(b -> b.getAddress() != null &&
                                b.getAddress().getNeighborhood() != null &&
                                b.getAddress().getNeighborhood().equalsIgnoreCase(body.getLocation()))
                        .collect(Collectors.toList());
            }

            List<PostBoardingResponse> newBoardings = new ArrayList<>();
            for (BoardingEntity boarding : boardings) {
                try {
                    PostBoardingResponse newBoarding = new PostBoardingResponse(
                            boarding.getId(),
                            boarding.getAddress(),
                            boarding.getPrice(),
                            boarding.getDriverId(),
                            boarding.getEventId(),
                            boarding.getTimeToGo(),
                            boarding.getTimeToOut(),
                            boarding.getAccountInBoarding() != null ? boarding.getAccountInBoarding().size() : 0
                    );

                    newBoardings.add(newBoarding);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            return newBoardings;
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
