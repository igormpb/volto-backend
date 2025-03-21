package com.igormpb.voltoja.domain.response;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AbacatePayCreatePixResponse {

    private String error;
    private Data data;

    @lombok.Data
    public static class Data {
        private List<Product> products;
        private long amount;
        private String status;
        private boolean devMode;
        private List<String> methods;
        private String frequency;
        private boolean allowCoupons;
        private List<String> coupons;
        private Map<String, Object> metadata;
        private String createdAt;
        private String updatedAt;
        private List<String> couponsUsed;
        private String url;
        private Customer customer;
        private String id;
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
        private Map<String, String> metadata;
    }
}
