package br.com.ufabc.poo.trocalivros.dto;

import br.com.ufabc.poo.trocalivros.model.Anuncio;
import br.com.ufabc.poo.trocalivros.model.Imagem;
import br.com.ufabc.poo.trocalivros.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 */
public class AnuncioDTO {
    @Getter private Long id;

    @Getter @Setter private String title;

    @Getter @Setter private String description;

    @Getter @Setter private BigDecimal price;

    @Getter @Setter private String condition;

    @Getter @Setter private List<Imagem> imagens;

    @Getter @Setter private List<String> categorias;

    @Getter @Setter private String usuarioCriador;

    @Getter @Setter private List<UsuarioDTO> usuariosInteressados;

    @Getter @Setter private Boolean active;

    public AnuncioDTO(Long id, String title, String description, BigDecimal price, String condition,
                      List<Imagem> imagens, List<String> categorias, String usuarioCriador,
                      List<UsuarioDTO> usuariosInteressados, Boolean active) {
        this.id = id;
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

    public static AnuncioDTO converterAnuncio(Anuncio anuncio){
        return new AnuncioDTO(anuncio.getId(), anuncio.getTitle(),
                anuncio.getDescription(), anuncio.getPrice(),
                anuncio.getCondition(), anuncio.getImagens(),
                anuncio.getCategorias(), anuncio.getUsuarioCriador(),
                anuncio.getUsuariosInteressados().stream().
                        map(UsuarioDTO::converterUsuario).collect(Collectors.toList()), anuncio.getActive());
    }
}
