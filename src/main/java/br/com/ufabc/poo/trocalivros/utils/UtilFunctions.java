package br.com.ufabc.poo.trocalivros.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilFunctions {
    public static String encode_password(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            return new String(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor."
            );
        }
    }

    public static void unauthorized(Boolean condition, String message) {
        if (!condition) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, message
            );
        }
    }

    public static void not_found(Boolean condition, String message) {
        if (!condition) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, message
            );
        }
    }
}
