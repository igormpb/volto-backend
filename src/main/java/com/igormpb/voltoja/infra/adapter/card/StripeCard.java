package com.igormpb.voltoja.infra.adapter.card;

import com.igormpb.voltoja.domain.adapter.ICardAdapter;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.response.CreateCheckoutPageAdapterResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class StripeCard implements ICardAdapter {

    public StripeCard() {
        Stripe.apiKey = "sk_test_51QmPpEHXofmbphjYHkgRar4vcxkaQEbkO67GcM0kOK9Oq3XNwGAKmk4cODPIfTYuMXefDTlj0Z1hmTsa43fRyHwg00So6QnFgS";
    }

    @Override
    public CreateCheckoutPageAdapterResponse CreateCheckoutPage(Long price, String name) throws HandleErros {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://flashja.digital/sucessor")
                    .setCancelUrl("https://flashja.digital/error")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder().setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("BRL")
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(name)
                                                                    .build()

                                                    )
                                                    .setUnitAmount(price)

                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            String idempotencyKey = UUID.randomUUID().toString();

            var requestOptions = RequestOptions.builder().setIdempotencyKey(idempotencyKey).build();
            Session session = Session.create(params, requestOptions);

            var response = new CreateCheckoutPageAdapterResponse(idempotencyKey, session.getUrl(), session.getAmountTotal(), session.getPaymentStatus());
            return response;
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new HandleErros("Não foi possível criar um pagamento", HttpStatus.NOT_FOUND);
        }
    }
}
