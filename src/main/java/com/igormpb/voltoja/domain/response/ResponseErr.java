package com.igormpb.voltoja.domain.response;

import org.springframework.http.HttpStatus;

public record ResponseErr(String message, HttpStatus status) {}
