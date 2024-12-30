package com.igormpb.voltoja.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCredentialsRegisterRequest {

    private String email = "";
    private String password = "";
    private String name = "";

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

        return null;
    }

}
