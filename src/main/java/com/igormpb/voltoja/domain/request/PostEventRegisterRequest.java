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
public class PostEventRegisterRequest {
    private String name = "";
    private String description = "";
    private AddressEntity address;
    private String producerId = "";
    private String eventDate = "";
    private String startTime = "";
    private String endTime = "";
    private String bannerUrl = "";
    private String photoUrl = "";
    private String type = "";
    private String createdAt = "";
    private String updatedAt = "";

    public String Validate() {
        if (this.name.isEmpty()) {
            return "nome é obrigatório";
        }
        if (this.type.isEmpty()) {
            return "tipo é obrigatório";
        }
        if (this.description.isEmpty()) {
            return "descrição é obrigatório";
        }
        if (this.producerId.isEmpty()) {
            return "Produtor é obrigatório";
        }
        if (this.eventDate.isEmpty()) {
            return "Data do evento é obrigatório";
        }
        if (this.startTime.isEmpty()) {
            return "Inicio do Evento é obrigatório";
        }
        if (this.endTime.isEmpty()) {
            return "Fim do Evento é obrigatório";
        }
        if (this.bannerUrl.isEmpty()) {
            return "Banner é obrigatório";
        }
        if (this.photoUrl.isEmpty()) {
            return "Foto é obrigatório";
        }
        return null;
    }
}
