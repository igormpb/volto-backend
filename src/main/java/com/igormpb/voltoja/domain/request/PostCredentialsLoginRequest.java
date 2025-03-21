package com.igormpb.voltoja.domain.request;


import com.igormpb.voltoja.infra.utils.Validators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCredentialsLoginRequest {

    private String email = "";
    private String password = "";

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

        return null;
    }


}