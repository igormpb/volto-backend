package com.igormpb.voltoja.infra.utils;

import com.danielfariati.validator.CNPJValidator;
import com.danielfariati.validator.CPFValidator;

import java.util.regex.Pattern;

public class Validators {

    public static boolean CPFOrCNPJIsValid(String document) {
        document = document.replaceAll("[^0-9]", "");
        if (document.length() == 14) {
            return new CNPJValidator().isValid(document, null);
        }
        return new CPFValidator().isValid(document, null);
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }


    public static boolean isCellPhoneValid(String phone) {
        phone = phone.replaceAll("[^0-9]", "");
        String phoneRegex = "^\\d{11}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return phone != null && pattern.matcher(phone).matches();
    }
}
