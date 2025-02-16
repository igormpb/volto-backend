package com.igormpb.voltoja.domain.adapter;

import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.response.CreateCheckoutPageAdapterResponse;

public interface ICardAdapter {

    public CreateCheckoutPageAdapterResponse CreateCheckoutPage(Long price, String name) throws HandleErros;
}
