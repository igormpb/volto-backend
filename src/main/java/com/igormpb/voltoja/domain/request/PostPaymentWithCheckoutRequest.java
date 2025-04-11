package com.igormpb.voltoja.domain.request;

import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.infra.utils.Validators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPaymentWithCheckoutRequest {

    private String document;
    private List<Customer> customer;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String name;
        private String email;
        private String phone;
        private String document;
    }

    public void Validate() {

        if (document.isEmpty()) {
            throw new HandleErros("Documento do responsável é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (!Validators.CPFOrCNPJIsValid(document)) {
            throw new HandleErros("Documento do responsável é inválido", HttpStatus.BAD_REQUEST);
        }

        for (Customer item : customer) {
            if (item.name == null || item.name.isEmpty()) {
                throw new HandleErros("Nome do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
            }

            if (item.email == null || item.email.isEmpty()) {
                throw new HandleErros("Email do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
            }

            if (item.document == null || item.document.isEmpty()) {
                throw new HandleErros("Documento do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
            }

            if (!Validators.CPFOrCNPJIsValid(item.document)) {
                throw new HandleErros(String.format("Documento do responsável %s é inválido", item.name), HttpStatus.BAD_REQUEST);
            }

            if (!Validators.isEmailValid(item.email)) {
                throw new HandleErros(String.format("Email do passageiro %s é inválido", item.name), HttpStatus.BAD_REQUEST);
            }

            if (item.phone == null || item.phone.isEmpty()) {
                throw new HandleErros("Número do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
            }

            if (!Validators.isCellPhoneValid(item.phone)) {
                throw new HandleErros(String.format("Número de celular do passageiro %s é inválido", item.name), HttpStatus.BAD_REQUEST);
            }
        }

    }


}
