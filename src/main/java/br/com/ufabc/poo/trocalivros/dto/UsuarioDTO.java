package br.com.ufabc.poo.trocalivros.dto;

import br.com.ufabc.poo.trocalivros.model.Usuario;
import lombok.Getter;
import lombok.Setter;

public class UsuarioDTO {
    @Getter @Setter private String username;
    @Getter @Setter private String email;
    @Getter @Setter private String name;
    @Getter @Setter private Boolean active;

    public UsuarioDTO(String username, String email, String name, Boolean active) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.active = active;
    }

    public static UsuarioDTO converterUsuario(Usuario usuario) {
        return new UsuarioDTO(usuario.getUsername(), usuario.getEmail(), usuario.getName(), usuario.getActive());
    }
}
