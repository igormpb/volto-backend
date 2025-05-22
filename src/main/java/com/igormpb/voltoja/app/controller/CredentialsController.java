package com.igormpb.voltoja.app.controller;


import com.igormpb.voltoja.app.service.CredentialsService;
import com.igormpb.voltoja.domain.adapter.ICardAdapter;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostCredentialsRegisterDriverRequest;
import com.igormpb.voltoja.domain.request.PostCredentialsSetPasswordRequest;
import com.igormpb.voltoja.infra.repository.AccountRepository;
import com.igormpb.voltoja.domain.request.PostCredentialsLoginRequest;
import com.igormpb.voltoja.domain.request.PostCredentialsRegisterRequest;
import com.igormpb.voltoja.domain.response.PostCredentialsLoginResponse;
import com.igormpb.voltoja.domain.response.ResponseErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/credentials")
public class CredentialsController {

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

    @PostMapping("/driver/register")
    public ResponseEntity RegisterDriver (@RequestBody PostCredentialsRegisterDriverRequest body) {
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            credentialsService.RegisterDriver(body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }

    }

    @PostMapping("/driver/register/password")
    public ResponseEntity RegisterDriverPassword (@RequestBody PostCredentialsSetPasswordRequest body) {
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            credentialsService.RegisterPasswordDriver(body);
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
            Map<String, Object> response = Map.of();

            if (!body.getDriver()) {
                response = credentialsService.Login(body);

            }
            if(body.getDriver()){
                response = credentialsService.LoginDriver(body);

            }
            return ResponseEntity.ok(new PostCredentialsLoginResponse(
                    (String) response.get("token"),
                    (String) response.get("email"),
                    (String) response.get("name"),
                    (String) response.get("phoneNumber"),
                    (String) response.get("accountId")
            ));
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }


}
