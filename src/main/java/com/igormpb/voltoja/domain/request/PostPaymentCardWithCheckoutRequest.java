package com.igormpb.voltoja.domain.request;

import com.igormpb.voltoja.domain.errors.HandleErros;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPaymentCardWithCheckoutRequest {

    private String name;
    private String email;
    private String phone;


    public void Validate() {

        if (name.isEmpty()) {
            throw new HandleErros("Nome do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (email.isEmpty()) {
            throw new HandleErros("Email do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (phone.isEmpty()) {
            throw new HandleErros("Número do passageiro é obrigatório", HttpStatus.BAD_REQUEST);
        }

    }


}
