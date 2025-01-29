package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostPaymentCardWithCheckoutRequest;
import com.igormpb.voltoja.infra.adapter.card.CardAdapterHandler;
import com.igormpb.voltoja.infra.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {


    @Autowired
    CardAdapterHandler cardAdapterHandler;
    EventRepository eventRepository;

    public String PaymentCardCheckoutURL(String eventUUID, List<PostPaymentCardWithCheckoutRequest> request) {
        try {
            var event = eventRepository.findById(eventUUID);

            if (event.isEmpty()) {
                throw new HandleErros("Evento não encontrado", HttpStatus.NOT_FOUND);
            }
            var name = event.get().getName() + request.size() + "X";
            var url = cardAdapterHandler.getAdapter().CreateCheckoutPage(100L, name);
            return url;
        }catch (HandleErros e) {
            throw e;
        }
        catch (Exception e) {
            throw new HandleErros("não foi listar os eventos, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

}
