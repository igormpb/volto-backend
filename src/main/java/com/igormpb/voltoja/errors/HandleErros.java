package com.igormpb.voltoja.errors;

import com.igormpb.voltoja.response.ResponseErr;
import org.springframework.http.HttpStatus;

public class HandleErros extends RuntimeException{
    private String message;
    private HttpStatus status;
    public  HandleErros(String message, HttpStatus status) {
        this.message = message;
        this.status = status;

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Serviço indisponivel");
        }
    }

    public ResponseErr GetResponseError()  {
        return new ResponseErr(this.message, this.status);
    }

}
