package com.igormpb.voltoja.domain.request;

import com.igormpb.voltoja.infra.utils.Validators;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostCredentialsRegisterDriverRequest {
    private String email = "";
    private String name = "";
    private String type = "";
    private String quantity = "";
    private String plate = "";
    private String phoneNumber = "";
    private Boolean music = false;
    private Boolean snow = false;
    private Boolean whats = false;
    private Boolean accept = false;

    public String Validate(){
        if(this.email.isEmpty()){
            return "e-mail é obrigatório";
        }
        if (!Validators.isEmailValid(this.email)) {
            return "e-mail é inválido";
        }
        if (this.name.isEmpty()) {
            return "nome é obrigatório";
        }
        if (this.type.isEmpty()) {
            return "tipo do veículo é obrigatório";
        }
        if (this.quantity.isEmpty()) {
            return "quantidade de passageiros é obrigatório";
        }
        if (this.plate.isEmpty()) {
            return "placa é obrigatório";
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
