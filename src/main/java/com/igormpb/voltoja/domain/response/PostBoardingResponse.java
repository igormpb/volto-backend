package com.igormpb.voltoja.domain.response;

import com.igormpb.voltoja.domain.entity.AddressEntity;

public record PostBoardingResponse (String id, AddressEntity address, Integer price, String driverId, String eventId, String timeToGo, String timeToOut, int accountInBoarding) {
}
