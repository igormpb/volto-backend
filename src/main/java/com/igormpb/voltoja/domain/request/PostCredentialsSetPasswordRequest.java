package com.igormpb.voltoja.domain.request;

import com.igormpb.voltoja.infra.utils.Validators;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostCredentialsSetPasswordRequest {
    private String id;
    private String password;

    public String Validate() {
        if (this.id.isEmpty()) {
            return "id é obrigatório";
        }

        if (this.password.isEmpty()) {
            return "senha é obrigatório";
        }

        return null;
    }
}
