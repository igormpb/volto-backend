package com.igormpb.voltoja.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class AcabatePayCreatePixRequest {
    private String frequency;
    private List<String> methods;
    private List<Product> products;
    private String returnUrl;
    private String completionUrl;
    private Customer customer;

    @Data
    public static class Product {
        @JsonProperty("externalId")
        private String externalId;
        private String name;
        private String description;
        private int quantity;
        private long price;
    }

    @Data
    public static class Customer {
        private String name;
        private String email;
        private String cellphone;
        @JsonProperty("taxId")
        private String taxId;
    }
}


