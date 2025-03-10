package com.igormpb.voltoja.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "account")
@TypeAlias("AccountEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {
    @Id
    private String id;
    private String name;
    private String email;
    @Field("phone_number")
    private String phoneNumber;
    private String password;
}
