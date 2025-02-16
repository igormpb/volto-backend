package com.igormpb.voltoja.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public record CreateCheckoutPageAdapterResponse(String id, String url, @JsonProperty("total_amount") Long totalAmount, String status) {
}

