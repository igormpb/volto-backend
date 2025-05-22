package com.igormpb.voltoja.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class AbacatePayWebhookReponse {
    private Data data;
    private String event;
    private boolean devMode;

    @lombok.Data
    public static class Data {
        private Billing billing;
        private Payment payment;
    }

    @lombok.Data
    public static class Billing {
        private int amount;
        private String status;
        private String frequency;
        private String id;
        private List<Product> products;
        private Customer customer;
        private List<String> kind;
        private int paidAmount;
        private List<String> couponsUsed;
    }

    @lombok.Data
    public static class Payment {
        private int amount;
        private int fee;
        private String method;
    }

    @lombok.Data
    public static class Product {
        private String id;
        private String externalId;
        private int quantity;
    }

    @lombok.Data
    public static class Customer {
        private String id;
        private Metadata metadata;
    }

    @lombok.Data
    public static class Metadata {
        private String cellphone;
        private String email;
        private String name;
        private String taxId;
    }

}
