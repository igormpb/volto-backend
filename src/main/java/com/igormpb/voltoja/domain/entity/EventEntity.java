package com.igormpb.voltoja.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "event")
@TypeAlias("EventEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private AddressEntity address;
    @Field("producer_id")
    private String producerId;
    @Field("event_date")
    private String eventDate;
    @Field("start_time")
    private String startTime;
    @Field("end_time")
    private String endTime;
    @Field("banner_url")
    private String bannerUrl;;
    private String type;
    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
    private String updatedAt;
}
