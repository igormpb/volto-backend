package com.igormpb.voltoja.infra.middleware;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igormpb.voltoja.domain.response.ResponseErr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthMiddleware implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response);
            return false;
        }

        token = token.substring(7); // Remove "Bearer "

        String accountId = extractAccountUuid(token);
        if (accountId == null) {
            sendUnauthorizedResponse(response);
            return false;
        }

        System.out.println(accountId);

        request.setAttribute("account_id", accountId);

        return true;
    }

    private String extractAccountUuid(String token) {
        try {
            String jwtSecret = System.getenv("JWT_SCREET");
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            return decodedJWT.getClaim("account_id").asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ResponseErr errorResponse = new ResponseErr("Operação não autorizada", HttpStatus.UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);


        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}
