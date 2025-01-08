package com.igormpb.voltoja.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "boarding")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardingEntity {


    @Id
    private String id;
    private AddressEntity address;
    private Integer price;


}
