package com.igormpb.voltoja.domain.response;

import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.entity.EventEntity;

public record GetBordingWithEventResponse(BoardingEntity boarding, EventEntity event) {

}
