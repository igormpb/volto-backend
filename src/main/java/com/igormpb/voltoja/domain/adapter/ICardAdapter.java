package com.igormpb.voltoja.domain.adapter;

import com.igormpb.voltoja.domain.errors.HandleErros;

public interface ICardAdapter {

    public String CreateCheckoutPage(Long price, String name) throws HandleErros;
}
