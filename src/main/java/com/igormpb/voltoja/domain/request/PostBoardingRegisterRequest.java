package com.igormpb.voltoja.domain.request;

import com.igormpb.voltoja.domain.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBoardingRegisterRequest {
    private Integer price = null ;
    private AddressEntity address;
    private String driverId ="";
    private String eventId = "";
    private String timeGo = "";
    private String timeOut = "";

    public String Validate() {
        if (this.price == null) {
            return "price é obrigatório";
        }
        if (this.driverId.isEmpty()) {
            return "Motorista é obrigatório";
        }
        if (this.eventId.isEmpty()) {
            return "Evento é obrigatório";
        }
        if (this.timeGo.isEmpty()) {
            return "Hora de Saída é obrigatório";
        }
        if (this.timeOut.isEmpty()) {
            return "Hora de Chegada é obrigatório";
        }


        return null;
    }
}
