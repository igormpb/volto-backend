package com.igormpb.voltoja.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

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
    @Field("time_go")
    private String timeToGo;
    @Field("time_out")
    private String timeToOut;
    @Field("account_in_boarding")
    private List<AccountInBoarding> accountInBoarding;

}
