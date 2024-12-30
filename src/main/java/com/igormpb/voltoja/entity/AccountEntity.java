package com.igormpb.voltoja.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String phone_number;
    private String password;
}
