package com.igormpb.voltoja.domain.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInBoarding {
    private String id;
    private String Name;
    private String Email;
    private String Phone;
    private String status;
}
