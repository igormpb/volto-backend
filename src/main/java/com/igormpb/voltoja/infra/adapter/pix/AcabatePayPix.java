package com.igormpb.voltoja.infra.adapter.pix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igormpb.voltoja.domain.adapter.IPixAdapter;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.AcabatePayCreatePixRequest;
import com.igormpb.voltoja.domain.response.AbacatePayCreatePixResponse;
import com.igormpb.voltoja.domain.response.CreateCheckoutPageAdapterResponse;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.UUID;

public class AcabatePayPix implements IPixAdapter {

    private static final String API_URL = "https://api.abacatepay.com/v1/billing/create";
    private static final String TOKEN = "abc_dev_gwh324MNsj1PeNJs23H3WfPk";

    @Override
    public CreateCheckoutPageAdapterResponse CreateCheckoutPage(Long price,String name, String document, String accountName, String email, String phoneNumber) {
        try {
            // Serializando o objeto para JSON usando Jackson
            var request = new AcabatePayCreatePixRequest();
            request.setFrequency("ONE_TIME");
            request.setMethods(java.util.List.of("PIX"));
            request.setReturnUrl("https://api.flashja.digital");
            request.setCompletionUrl("https://flashja.digital/sucessor");


            var productRequest = new AcabatePayCreatePixRequest.Product();
            String idempotencyKey = UUID.randomUUID().toString();

            productRequest.setQuantity(1);
            productRequest.setPrice(price);
            productRequest.setName(name);
            productRequest.setExternalId(idempotencyKey);
            productRequest.setDescription(idempotencyKey);


            request.setProducts(java.util.List.of(productRequest));
            var customerRequest = new AcabatePayCreatePixRequest.Customer();
            customerRequest.setName(accountName);
            customerRequest.setEmail(email);
            customerRequest.setCellphone(phoneNumber);
            customerRequest.setTaxId(document);
            request.setCustomer(customerRequest);



            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(request);

            // Realizando a requisição POST

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest sendApi = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(sendApi,
                    HttpResponse.BodyHandlers.ofString());

            // Verificando a resposta da API
            if (response.statusCode() != 200) {
                throw new HandleErros("Ocorreu um error ao criar um pagamento via pix, por favor verificar e tentar novamente", HttpStatus.BAD_REQUEST);
            }

            var result = objectMapper.readValue(response.body(), AbacatePayCreatePixResponse.class);
            return new CreateCheckoutPageAdapterResponse(result.getData().getId(), result.getData().getUrl(), result.getData().getAmount(), result.getData().getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            throw new HandleErros("Ocorreu um error ao criar um pagamento via pix, por favor verificar e tentar novamente", HttpStatus.BAD_REQUEST);

        }
    }
}
