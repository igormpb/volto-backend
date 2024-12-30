package com.igormpb.voltoja.response;

import org.springframework.http.HttpStatus;

public record ResponseErr(String message, HttpStatus status) {}
