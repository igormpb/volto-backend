package com.igormpb.voltoja.domain.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "driver")
@TypeAlias("DriverEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DriverEntity {


    @Id
    private String id;
    private String name;
    private String type;
    private String quantity;
    private String plate;
    private ArrayList<String> documents;
    private Boolean music;
    private Boolean snow;
}
