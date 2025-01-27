package com.igormpb.voltoja.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDriverRegisterRequest {
    private String name = "";
    private String type = "";
    private String quantity = "";
    private String plate = "";
    private ArrayList<String> documents = new ArrayList<>();
    private Boolean music = false;
    private Boolean snow = false;

    public String Validate() {
        if (this.name.isEmpty()) {
            return "nome é obrigatório";
        }

        if (this.type.isEmpty()) {
            return "tipo é obrigatório";
        }

        if (this.quantity.isEmpty()) {
            return "quantidade de vagas é obrigatório";
        }

        if (this.plate.isEmpty()) {
            return "placa é obrigatório";
        }

        if (this.documents.stream().anyMatch(String::isEmpty)) {
            return "documentos são obrigatório";
        }

        return null;
    }
}
