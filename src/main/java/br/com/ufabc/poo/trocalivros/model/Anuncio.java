package br.com.ufabc.poo.trocalivros.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade referente aos Anúncios
 */
@Entity
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Long id;

    @Getter @Setter private String title;

    @Getter @Setter private String description;

    @Getter @Setter private BigDecimal price;

    @Getter @Setter private String condition;

    @ElementCollection(targetClass = Imagem.class)

    @OneToMany(cascade = CascadeType.ALL)
    @Getter @Setter private List<Imagem> imagens;

    @ElementCollection(targetClass = String.class)
    @Getter @Setter private List<String> categorias;

    @Getter @Setter private String usuarioCriador;

    @OneToMany(cascade = {CascadeType.ALL})
    @ElementCollection(targetClass = Usuario.class)
    @Getter @Setter private List<Usuario> usuariosInteressados;

    @Getter @Setter private Boolean active;

    public Anuncio() {}

    public Anuncio(String title, String description, BigDecimal price, String condition, List<Imagem> imagens, List<String> categorias, String usuarioCriador, List<Usuario> usuariosInteressados, Boolean active) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.imagens = imagens;
        this.categorias = categorias;
        this.usuarioCriador = usuarioCriador;
        this.usuariosInteressados = usuariosInteressados;
        this.active = active;
    }
}
