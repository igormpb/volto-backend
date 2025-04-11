package com.igormpb.voltoja.domain.entity;

public class ControlConfig {
    public static Integer getTax(Integer price){
        return (price * 10)/100;
    }
}
