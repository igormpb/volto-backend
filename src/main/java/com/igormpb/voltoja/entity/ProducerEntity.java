package com.igormpb.voltoja.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "producer")
@TypeAlias("ProducerEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerEntity {
    @Id
    private String id;
    private String name;
    @Field("photo_url")
    private String photoUrl;
    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
    private String updatedAt;
}
