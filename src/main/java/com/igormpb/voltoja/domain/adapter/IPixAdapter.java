package com.igormpb.voltoja.domain.adapter;

import com.igormpb.voltoja.domain.entity.AccountEntity;
import com.igormpb.voltoja.domain.response.CreateCheckoutPageAdapterResponse;

public interface IPixAdapter {

    public CreateCheckoutPageAdapterResponse CreateCheckoutPage(Long price,String name, String document, String accountName, String email, String phoneNumber);
}
