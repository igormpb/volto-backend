package com.igormpb.voltoja.request;

import com.igormpb.voltoja.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBoardingRegisterRequest {
    private Integer price = null ;
    private AddressEntity address;
    private String driverId ="";
    private String eventId = "";

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

        return null;
    }
}
