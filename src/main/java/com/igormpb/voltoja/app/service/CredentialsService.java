package com.igormpb.voltoja.app.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.igormpb.voltoja.domain.entity.AccountEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.infra.repository.AccountRepository;
import com.igormpb.voltoja.domain.request.PostCredentialsLoginRequest;
import com.igormpb.voltoja.domain.request.PostCredentialsRegisterRequest;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    @Autowired
    AccountRepository accountRepository;

    public void Register(PostCredentialsRegisterRequest body) {
        try {
            String passwordHash = BCrypt.withDefaults().hashToString(12, body.getPassword().toCharArray());
            var account = AccountEntity.builder()
                    .email(body.getEmail())
                    .password(passwordHash)
                    .name(body.getName())
                    .build();

            var result = accountRepository.findByEmail(body.getEmail());
            if (result != null) {
                throw new HandleErros("conta já existe, por favor tente realizar o login", HttpStatus.BAD_REQUEST);
            }

            accountRepository.save(account);
        } catch (MongoException e) {
            throw new HandleErros("não foi possível criar sua conta, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);

        }

    }


    public String Login(PostCredentialsLoginRequest body) {
        try {
            String jwtSecret = System.getenv("JWT_SCREET");

            if (jwtSecret == null) {
                jwtSecret = "dev";
            }

            var account = accountRepository.findByEmail(body.getEmail());
            if (account == null || account.getId().isEmpty()) {
                throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
            }
            if (!BCrypt.verifyer().verify(body.getPassword().getBytes(), account.getPassword().getBytes()).verified) {
                throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
            }

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create().withClaim("email", account.getEmail()).sign(algorithm).toString();
        } catch (MongoException e) {
            throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new HandleErros("não foi possível realizar o login, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
}
