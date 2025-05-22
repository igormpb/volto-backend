package com.igormpb.voltoja.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import com.igormpb.voltoja.domain.response.AbacatePayWebhookReponse;
import com.igormpb.voltoja.infra.repository.BoardingRepository;
import com.igormpb.voltoja.infra.repository.CheckoutRepository;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
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
                case "checkout.session.completed":
                    Session session = (Session) stripeObject;
                    System.out.println("💰 Pagamento recebido: " + session.getId());
                    if (!session.getPaymentStatus().equals("paid")) {
                        System.out.println("pagamento nao foi finalizado");
                        break;
                    }
                    var checkout = checkoutRepository.findByPaymentId(session.getId());
                    if (checkout == null) {
                        System.out.println("pagamento nao encontradao");
                        break;
                    }
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
                default:
                    System.out.println("ℹ️ Evento não tratado: " + event.getType());
                    break;
            }

            return ResponseEntity.ok("✅ Webhook recebido com sucesso!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro interno ao processar webhook.");
        }
    }


    @PostMapping("/acabatepay")
    public ResponseEntity<String> handleAbacatePayWebhook(HttpServletRequest request) {
        try {
            String payload = new BufferedReader(request.getReader()).lines().collect(Collectors.joining("\n"));
            System.out.println("✅ Payload recebido: " + payload);

            ObjectMapper objectMapper = new ObjectMapper();
            AbacatePayWebhookReponse webhookPayload = objectMapper.readValue(payload, AbacatePayWebhookReponse.class);

            // Verifica tipo de evento
            if ("billing.paid".equals(webhookPayload.getEvent())) {
                String billingId = webhookPayload.getData().getBilling().getId();
                String customerId = webhookPayload.getData().getBilling().getCustomer().getId();
                int amount = webhookPayload.getData().getBilling().getPaidAmount();

                System.out.println("💰 Pagamento PIX recebido!");
                System.out.println("🔖 Billing ID: " + billingId);
                System.out.println("👤 Customer ID: " + customerId);
                System.out.println("💲 Valor pago: " + amount);

                // Busca o checkout no banco pelo Billing ID
                var checkout = checkoutRepository.findByPaymentId(billingId);
                if (checkout == null) {
                    System.out.println("⚠️ Checkout não encontrado para Billing ID: " + billingId);
                    return ResponseEntity.badRequest().body("⚠️ Checkout não encontrado.");
                }

                // Atualiza status para PAID
                checkout.setStatus("PAID");
                var boardingID = checkout.getBoardingId();

                // Busca o boarding
                var itemBoarding = boardingReposity.findById(boardingID);
                if (itemBoarding.isEmpty()) {
                    System.out.println("⚠️ Boarding não encontrado para ID: " + boardingID);
                    return ResponseEntity.badRequest().body("⚠️ Boarding não encontrado.");
                }

                var accountId = checkout.getAccountId();
                var boarding = itemBoarding.get();

                // Atualiza status da conta no boarding
                boarding.getAccountInBoarding().forEach(acc -> {
                    if (acc.getId().equals(accountId)) {
                        acc.setStatus("PAID");
                    }
                });

                // Salva as alterações
                checkoutRepository.save(checkout);
                boardingReposity.save(boarding);

                System.out.println("✅ Checkout e Boarding atualizados com sucesso.");

                return ResponseEntity.ok("✅ Webhook PIX processado e atualizado com sucesso!");

            } else {
                System.out.println("ℹ️ Evento não tratado: " + webhookPayload.getEvent());
                return ResponseEntity.ok("✅ Webhook recebido, mas evento não tratado.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Erro interno ao processar webhook PIX.");
        }

    }
}
