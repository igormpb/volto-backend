package com.igormpb.voltoja.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostProducerRegisterRequest {
    private String name = "";
    private String photoUrl = "";

    public String Validate() {
        if (this.name.isEmpty()) {
            return "nome é obrigatório";
        }
        if (this.photoUrl.isEmpty()) {
            return "Foto é obrigatório";
        }
        return null;
    }
}
