package com.igormpb.voltoja.domain.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInBoarding {
    private String id;
    @Field("account_id")
    private String accountId;
    private String Name;
    private String Email;
    private String Documents;
    private String Phone;
    private String status;
}
