package com.igormpb.voltoja.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "boarding")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardingEntity {


    @Id
    private String id;
    private AddressEntity address;
    private Integer price;
    @Field("driver_id")
    private String driverId;
    @Field("event_id")
    private String eventId;

}
