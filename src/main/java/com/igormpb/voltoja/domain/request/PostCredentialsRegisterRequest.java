package com.igormpb.voltoja.domain.request;

import com.igormpb.voltoja.infra.utils.Validators;
import lombok.*;

@Getter
@Setter
@Builder
public class PostCredentialsRegisterRequest {

    private String email = "";
    private String password = "";
    private String name = "";
    private String phoneNumber = "";

    public String Validate() {
        if (this.email.isEmpty()) {
            return "e-mail é obrigatório";
        }

        if (!Validators.isEmailValid(this.email)) {
            return "e-mail é inválido";
        }

        if (this.password.isEmpty()) {
            return "senha é obrigatório";
        }

        if (this.name.isEmpty()) {
            return "nome é obrigatório";
        }

        if (this.phoneNumber.isEmpty()) {
            return "número celular é obrigatório";
        }

        if (!Validators.isCellPhoneValid(this.phoneNumber)) {
            System.out.println(this.phoneNumber);
            return "número de celular é inválido";
        }


        return null;
    }

}
