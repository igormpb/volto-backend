package com.igormpb.voltoja.domain.request;

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

        if (this.password.isEmpty()) {
            return "senha é obrigatório";
        }

        if (this.name.isEmpty()) {
            return "nome é obrigatório";
        }
        if (this.phoneNumber.isEmpty()) {
            return "número é obrigatório";
        }

        return null;
    }

}
