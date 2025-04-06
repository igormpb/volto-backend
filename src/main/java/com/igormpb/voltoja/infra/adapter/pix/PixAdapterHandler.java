package com.igormpb.voltoja.infra.adapter.pix;

import com.igormpb.voltoja.domain.adapter.ICardAdapter;
import com.igormpb.voltoja.domain.adapter.IPixAdapter;
import com.igormpb.voltoja.infra.adapter.card.StripeCard;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class PixAdapterHandler {
    private final Map<String, IPixAdapter> adapters = new HashMap<>();
    private final String adapterKey = "abacatepay";
    public PixAdapterHandler() {
        adapters.put("abacatepay", new AcabatePayPix());
    }

    public IPixAdapter getAdapter() {
        return adapters.get(adapterKey);
    }
}


