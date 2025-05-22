package com.igormpb.voltoja.app.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.igormpb.voltoja.domain.entity.AccountEntity;
import com.igormpb.voltoja.domain.entity.DriverEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostCredentialsRegisterDriverRequest;
import com.igormpb.voltoja.domain.request.PostCredentialsSetPasswordRequest;
import com.igormpb.voltoja.infra.repository.AccountRepository;
import com.igormpb.voltoja.domain.request.PostCredentialsLoginRequest;
import com.igormpb.voltoja.domain.request.PostCredentialsRegisterRequest;
import com.igormpb.voltoja.infra.repository.DriverRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CredentialsService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    DriverRepository driverRepository;

    public void Register(PostCredentialsRegisterRequest body) {
        try {
            String passwordHash = BCrypt.withDefaults().hashToString(12, body.getPassword().toCharArray());
            var account = AccountEntity.builder()
                    .email(body.getEmail())
                    .password(passwordHash)
                    .name(body.getName())
                    .phoneNumber(body.getPhoneNumber())
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

    public void RegisterDriver(PostCredentialsRegisterDriverRequest body) {
        try {
            var account = DriverEntity.builder()
                    .email(body.getEmail())
                    .name(body.getName())
                    .phoneNumber(body.getPhoneNumber())
                    .type(body.getType())
                    .quantity(body.getQuantity())
                    .plate(body.getPlate())
                    .music(body.getMusic())
                    .snow(body.getSnow())
                    .whats(body.getWhats())
                    .accept(body.getAccept())
                    .build();

            var result = driverRepository.findByPlate(body.getPlate());
            if (result != null) {
                throw new HandleErros("veículo já cadastrado, por favor tente realizar o login", HttpStatus.BAD_REQUEST);
            }

            driverRepository.save(account);
        } catch (MongoException e) {
            throw new HandleErros("não foi possível cadastrar sua veículo, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);

        }

    }

    public void RegisterPasswordDriver(PostCredentialsSetPasswordRequest body) {
        try {
            var driver = driverRepository.findById(body.getId()).get();
            String passwordHash = BCrypt.withDefaults().hashToString(12, body.getPassword().toCharArray());
            driver.setPassword(passwordHash);
            driverRepository.save(driver);
        } catch (MongoException e) {
            throw new HandleErros("não foi possível registrar a senha do veículo, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);

        }

    }

    public Map<String, Object> Login(PostCredentialsLoginRequest body) {
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
            String token = JWT.create().withClaim("account_id", account.getId()).sign(algorithm);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", account.getEmail());
            response.put("name", account.getName());
            response.put("phoneNumber", account.getPhoneNumber());
            response.put("accountId", account.getId());

            return response;
        } catch (MongoException e) {
            throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro para depuração
            throw new HandleErros("não foi possível realizar o login, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public Map<String, Object> LoginDriver(PostCredentialsLoginRequest body) {
        try {
            String jwtSecret = System.getenv("JWT_SCREET");

            if (jwtSecret == null) {
                jwtSecret = "dev";
            }

            var account = driverRepository.findDriverByEmail(body.getEmail());
            if (account == null || account.getId().isEmpty()) {
                throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
            }
            if (!BCrypt.verifyer().verify(body.getPassword().getBytes(), account.getPassword().getBytes()).verified) {
                throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
            }

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            String token = JWT.create().withClaim("account_id", account.getId()).sign(algorithm);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", account.getEmail());
            response.put("name", account.getName());
            response.put("phoneNumber", account.getPhoneNumber());
            response.put("accountId", account.getId());

            return response;
        } catch (MongoException e) {
            throw new HandleErros("e-mail e/ou senha invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro para depuração
            throw new HandleErros("não foi possível realizar o login, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
}
