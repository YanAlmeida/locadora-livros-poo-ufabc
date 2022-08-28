package br.com.ufabc.poo.trocalivros.model;

import lombok.Getter;

/**
 * Classe referente ao login do usuário
 */
public class AuthUser {
    @Getter private final String username;
    @Getter private final String password;

    public AuthUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
