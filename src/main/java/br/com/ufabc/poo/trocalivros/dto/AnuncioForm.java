package br.com.ufabc.poo.trocalivros.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import br.com.ufabc.poo.trocalivros.model.Usuario;
import br.com.ufabc.poo.trocalivros.model.Anuncio;
import br.com.ufabc.poo.trocalivros.model.Imagem;
import lombok.Getter;
import lombok.Setter;
public class AnuncioForm {
    @Getter @Setter private Long id;
    @Getter @Setter private String title;
    @Getter @Setter private String description;
    @Getter @Setter private BigDecimal price;
    @Getter @Setter private String condition;
    @Getter @Setter private Boolean active;
    @Getter @Setter private String usuarioCriador;
    @Getter @Setter private List<Usuario> usuariosInteressados = new ArrayList<Usuario>();
    @Getter @Setter private List<Imagem> imagens = new ArrayList<Imagem>();
    @Getter @Setter private List<String> categorias = new ArrayList<String>();
    
    
    public AnuncioForm(Long id, String title, String description, BigDecimal price, String condition, Boolean active,
            String usuarioCriador, List<Usuario> usuariosInteressados) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.active = active;
        this.usuarioCriador = usuarioCriador;
        this.usuariosInteressados = usuariosInteressados;
    }

    /**
     * Método responsável para converter de UsuarioForm para Usuario
     * @return Objeto convertido para Usuario
     */
    public Anuncio toAnuncio() {
        return new Anuncio(title, description, price, condition, imagens, categorias, usuarioCriador, usuariosInteressados, active);
    }
}
