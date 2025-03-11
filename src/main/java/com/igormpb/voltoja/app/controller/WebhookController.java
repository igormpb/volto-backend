package com.igormpb.voltoja.app.controller;

import com.google.gson.JsonSyntaxException;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.infra.repository.CheckoutRepository;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webhook")
public class WebhookController {


    @Autowired
    CheckoutRepository checkoutRepository;

    @Autowired
    BoardingRepository boardingReposity;


    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        try {
            String payload = new BufferedReader(request.getReader()).lines().collect(Collectors.joining("\n"));
            String sigHeader = request.getHeader("Stripe-Signature");

            Event event;

            Stripe.apiKey = "sk_test_51QmPpEHXofmbphjYHkgRar4vcxkaQEbkO67GcM0kOK9Oq3XNwGAKmk4cODPIfTYuMXefDTlj0Z1hmTsa43fRyHwg00So6QnFgS";
            try {
                event = Webhook.constructEvent(payload, sigHeader, "whsec_MSP4aN0WEw7jl2cUE6wjQfVUIXRhh3Pd");
            } catch (JsonSyntaxException e) {
                return ResponseEntity.badRequest().body("⚠️ Erro ao analisar JSON do Webhook.");
            } catch (SignatureVerificationException e) {
                return ResponseEntity.badRequest().body("⚠️ Falha na verificação da assinatura do Webhook.");
            }

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = dataObjectDeserializer.getObject().orElse(null);

            if (stripeObject == null) {
                return ResponseEntity.badRequest().body("⚠️ Erro ao desserializar evento Stripe.");
            }

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    System.out.println("💰 Pagamento recebido: " + paymentIntent.getId());
                    var item = checkoutRepository.findById(paymentIntent.getId());
                    if (item.isEmpty()) {
                        System.out.println("pagamento nao encontradao");
                        break;
                    }
                    var checkout = item.get();
                    checkout.setStatus("PAID");
                    var boardingID = checkout.getBoardingId();

                    var itemBoarding = boardingReposity.findById(boardingID);
                    if (itemBoarding.isEmpty()) {
                        System.out.println("pagamento nao encontradao");
                        break;
                    }

                    var accountId = checkout.getAccountId();
                    var boarding = itemBoarding.get();
                    boarding.getAccountInBoarding().forEach(acc -> {
                        if (acc.getId().equals(accountId)) {
                            acc.setStatus("PAID");
                        }
                    });

                    checkoutRepository.save(checkout);
                    boardingReposity.save(boarding);

                    break;
                case "payment_method.attached":
                    PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                    System.out.println("📌 Método de pagamento anexado: " + paymentMethod.getId());
                    break;
                default:
                    System.out.println("ℹ️ Evento não tratado: " + event.getType());
                    break;
            }

            return ResponseEntity.ok("✅ Webhook recebido com sucesso!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro interno ao processar webhook.");
        }
    }
}
