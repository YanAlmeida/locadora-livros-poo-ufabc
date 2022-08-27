package br.com.ufabc.poo.trocalivros.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {
    @Id
    @Getter @Setter private String username;
    @Getter @Setter private String email;
    @Getter @Setter private String password;
    @Getter @Setter private String name;
    @Getter @Setter private Boolean active;

    public Usuario() {}

    public Usuario(String username, String email, String password, String name, Boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.active = active;
    }
}
