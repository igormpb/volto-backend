package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.entity.AccountInBoarding;
import com.igormpb.voltoja.domain.entity.CheckoutEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostPaymentCardWithCheckoutRequest;
import com.igormpb.voltoja.infra.adapter.card.CardAdapterHandler;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.infra.repository.CheckoutRepository;
import com.igormpb.voltoja.infra.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {


    @Autowired
    CardAdapterHandler cardAdapterHandler;
    @Autowired
    BoardingRepository boardingRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    CheckoutRepository checkoutRepository;

    public String PaymentCardCheckoutURL(String boardingUUID, List<PostPaymentCardWithCheckoutRequest> request, String accountId) {
        System.out.println(boardingUUID);
        try {
            var boarding = boardingRepository.findById(boardingUUID);
            if (boarding.isEmpty()) {
                throw new HandleErros("Embarque não encontrado", HttpStatus.NOT_FOUND);
            }
            var event = eventRepository.findById(boarding.get().getEventId());
            if (event.isEmpty()) {
                throw new HandleErros("Evento não encontrado", HttpStatus.NOT_FOUND);
            }

            var boardingEntity = boarding.get();
            var eventEntity = event.get();
            var name = eventEntity.getName() +" "+ request.size() + "X";
            var price = new Long(boardingEntity.getPrice() * request.size());
            var response = cardAdapterHandler.getAdapter().CreateCheckoutPage(price, name);

            //TODO PEGAR O ACCOUNT_ID
            var data = CheckoutEntity.builder().status(response.status()).paymentId(response.id()).url(response.url()).paymentAt("").updatedAt("").createdAt("").accountId("").boardingId(boardingEntity.getId()).eventId(eventEntity.getId()).build();

            checkoutRepository.save(data);
            if (boardingEntity.getAccountInBoarding() == null) {
                boardingEntity.setAccountInBoarding(new ArrayList<>());
            }

            boardingEntity.getAccountInBoarding().add(
                    AccountInBoarding.builder().id(accountId).status("PENDING").build()
            );

            boardingRepository.save(boardingEntity);
            return response.url();
        }catch (HandleErros e) {
            throw e;
        }
        catch (Exception e) {

            System.out.println(e);
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

}
