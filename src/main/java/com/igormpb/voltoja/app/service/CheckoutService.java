package com.igormpb.voltoja.app.service;

import com.igormpb.voltoja.domain.entity.AccountInBoarding;
import com.igormpb.voltoja.domain.entity.CheckoutEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostPaymentWithCheckoutRequest;
import com.igormpb.voltoja.infra.adapter.card.CardAdapterHandler;
import com.igormpb.voltoja.infra.adapter.pix.PixAdapterHandler;
import com.igormpb.voltoja.infra.repository.AccountRepository;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.infra.repository.CheckoutRepository;
import com.igormpb.voltoja.infra.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CheckoutService {


    @Autowired
    CardAdapterHandler cardAdapterHandler;
    @Autowired
    PixAdapterHandler pixAdapterHandler;
    @Autowired
    BoardingRepository boardingRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    CheckoutRepository checkoutRepository;
    @Autowired
    AccountRepository accountRepository;

    public String PaymentCardCheckoutCardURL(String boardingUUID, PostPaymentWithCheckoutRequest request, String accountId) {
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
            var name = eventEntity.getName() +" "+ request.getCustomer().size() + "X";
            var price =  (long) boardingEntity.getPrice() * request.getCustomer().size();
            var response = cardAdapterHandler.getAdapter().CreateCheckoutPage(price, name);

            var unicId = UUID.randomUUID().toString();
            //TODO PEGAR O ACCOUNT_ID
            var data = CheckoutEntity.builder().status(response.status()).paymentId(response.id()).url(response.url()).paymentAt("").updatedAt(LocalDate.now().toString()).createdAt(LocalDate.now().toString()).accountId(unicId).boardingId(boardingEntity.getId()).eventId(eventEntity.getId()).build();

            checkoutRepository.save(data);
            if (boardingEntity.getAccountInBoarding() == null) {
                boardingEntity.setAccountInBoarding(new ArrayList<>());
            }


            request.getCustomer().forEach(r -> {
                boardingEntity.getAccountInBoarding().add(
                        AccountInBoarding.builder().id(unicId).accountId(accountId).Email(r.getEmail()).Phone(r.getPhone()).Documents(r.getDocument()).Name(r.getName()).status("PENDING").build()
                );
            });

            boardingRepository.save(boardingEntity);
            return response.url();
        }catch (HandleErros e) {
            throw e;
        }
        catch (Exception e) {

            System.out.println(e);
            throw new HandleErros("não foi finalizar o carrinho, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }


    public String PaymentCardCheckoutPixURL(String boardingUUID, PostPaymentWithCheckoutRequest request, String accountId) {
        try {
            var boarding = boardingRepository.findById(boardingUUID);
            if (boarding.isEmpty()) {
                throw new HandleErros("Embarque não encontrado", HttpStatus.NOT_FOUND);
            }
            var event = eventRepository.findById(boarding.get().getEventId());
            if (event.isEmpty()) {
                throw new HandleErros("Evento não encontrado", HttpStatus.NOT_FOUND);
            }

            var account = accountRepository.findById(accountId);
            if (account.isEmpty()) {
                throw new HandleErros("Conta não encontrado", HttpStatus.NOT_FOUND);
            }

            var boardingEntity = boarding.get();
            var eventEntity = event.get();
            var accountEntity = account.get();

            var name = eventEntity.getName() +" "+ request.getCustomer().size() + "X";
            var price =  (long) boardingEntity.getPrice() * request.getCustomer().size();
            var response = pixAdapterHandler.getAdapter().CreateCheckoutPage(price, name, request.getDocument(),accountEntity.getName(), accountEntity.getEmail(), accountEntity.getPhoneNumber());

            var unicId = UUID.randomUUID().toString();
            //TODO PEGAR O ACCOUNT_ID
            var data = CheckoutEntity.builder().status(response.status()).paymentId(response.id()).url(response.url()).paymentAt("").updatedAt(LocalDate.now().toString()).createdAt(LocalDate.now().toString()).accountId(unicId).boardingId(boardingEntity.getId()).eventId(eventEntity.getId()).build();

            checkoutRepository.save(data);
            if (boardingEntity.getAccountInBoarding() == null) {
                boardingEntity.setAccountInBoarding(new ArrayList<>());
            }


            request.getCustomer().forEach(r -> {
                boardingEntity.getAccountInBoarding().add(
                        AccountInBoarding.builder().id(unicId).accountId(accountId).Email(r.getEmail()).Phone(r.getPhone()).Documents(r.getDocument()).Name(r.getName()).status("PENDING").build()
                );
            });

            boardingRepository.save(boardingEntity);
            return response.url();
        }catch (HandleErros e) {
            throw e;
        }
        catch (Exception e) {

            System.out.println(e);
            throw new HandleErros("não foi finalizar o carrinho, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

}
