package com.ufrn.imd.ecommerce.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;



    public String generateToken(UsuarioConcreto usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-user")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro enquanto tentava gerar token");
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    // other methods

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            logger.debug("Validating token: {}", token);
            String subject = JWT.require(algorithm)
                    .withIssuer("auth-user")
                    .build()
                    .verify(token)
                    .getSubject();
            logger.debug("Token is valid. Subject: {}", subject);
            return subject;
        } catch (JWTVerificationException ex) {
            logger.error("Token verification failed", ex);
            return "";
        }
    }


/*
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = token.trim();
            return JWT.require(algorithm)
                    .withIssuer("auth-user")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }
*/
    public String resolveToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }

        return authHeader.replace("Bearer", "").trim();
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
