package com.igormpb.voltoja.domain.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "checkout")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CheckoutEntity {


    @Id
    private String id;
    private String status;
    private String url;
    @Field("payment_id")
    private String paymentId;
    @Field("account_id")
    private String accountId;
    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
    private String updatedAt;
    @Field("payment_at")
    private String paymentAt;
    @Field("boarding_id")
    private String boardingId;
    @Field("event_id")
    private String eventId;
}
