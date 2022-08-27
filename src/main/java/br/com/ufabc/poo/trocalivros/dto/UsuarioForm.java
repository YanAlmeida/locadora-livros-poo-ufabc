package br.com.ufabc.poo.trocalivros.dto;

import br.com.ufabc.poo.trocalivros.model.Usuario;
import lombok.Getter;
import lombok.Setter;

public class UsuarioForm {

    @Getter @Setter private String username;
    @Getter @Setter private String password;
    @Getter @Setter private String email;
    @Getter @Setter private String name;
    @Getter @Setter private Boolean active;

    public UsuarioForm(String username, String password, String email, String name, Boolean active) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.active = active;
    }

    public Usuario toUsuario() {
        return new Usuario(username, email, password, name, active);
    }
}
