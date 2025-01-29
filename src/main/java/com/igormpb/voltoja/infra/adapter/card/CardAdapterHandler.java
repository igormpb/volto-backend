package com.igormpb.voltoja.infra.adapter.card;

import com.igormpb.voltoja.domain.adapter.ICardAdapter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CardAdapterHandler {

    private final Map<String, ICardAdapter> adapters = new HashMap<>();
    private final String adapterKey = "stripe";
    public CardAdapterHandler() {
        adapters.put("stripe", new StripeCard());
    }


    public ICardAdapter getAdapter() {
        return adapters.get(adapterKey);
    }
}
