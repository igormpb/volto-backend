package com.igormpb.voltoja.controller;


import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.repository.AccountRepository;
import com.igormpb.voltoja.request.PostCredentialsLoginRequest;
import com.igormpb.voltoja.request.PostCredentialsRegisterRequest;
import com.igormpb.voltoja.response.PostCredentialsLoginResponse;
import com.igormpb.voltoja.response.ResponseErr;
import com.igormpb.voltoja.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credentials")
public class CredentialsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CredentialsService credentialsService;


    @PostMapping("/register")
    public ResponseEntity Register (@RequestBody PostCredentialsRegisterRequest body) {
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            credentialsService.Register(body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }

    }



    @PostMapping("/login")
    public ResponseEntity Login (@RequestBody PostCredentialsLoginRequest body) {
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            String token = credentialsService.Login(body);
            return ResponseEntity.ok(new PostCredentialsLoginResponse(token));
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

}
