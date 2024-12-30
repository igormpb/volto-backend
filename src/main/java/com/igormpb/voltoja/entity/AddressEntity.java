package com.igormpb.voltoja.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String country;
    private String state;
}
