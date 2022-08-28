package br.com.ufabc.poo.trocalivros.controller;

import br.com.ufabc.poo.trocalivros.dto.AnuncioDTO;
import br.com.ufabc.poo.trocalivros.dto.UsuarioForm;
import br.com.ufabc.poo.trocalivros.model.Anuncio;
import br.com.ufabc.poo.trocalivros.model.Usuario;
import br.com.ufabc.poo.trocalivros.service.AnuncioService;
import br.com.ufabc.poo.trocalivros.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnuncioController {
    @Autowired
    private AnuncioService anuncioService;
    
    /**
     * <p>Endpoint para criação de um novo anúncio</p>
     * @param newAnuncio Objeto com as propriedades do novo anúncio no BODY
     * @param token Token de autenticação no HEADER
     * @return Objeto do novo anúncio
     */
    @PostMapping("/anuncios")
    public AnuncioDTO createAnuncio(@RequestBody Anuncio newAnuncio, @RequestHeader(value="AuthToken") String token){
        AutenticacaoService.verifyToken(token);
        return AnuncioDTO.converterAnuncio(anuncioService.createAnuncio(newAnuncio));
    }

    /**
     * <p>Endpoint para atualização de um anúncio</p>
     * @param id Identificador do anúncio a ser atualizado no PATH
     * @param updatedAnuncio Objeto do anúncio a ser atualizado no BODY
     * @param token Token de autenticação no HEADER
     * @return Objeto do anúncio atualizado
     */
    @PutMapping("/anuncios/{id}")
    public AnuncioDTO updateAnuncio(@PathVariable Long id, @RequestBody Anuncio updatedAnuncio,
                                    @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return AnuncioDTO.converterAnuncio(anuncioService.updateAnuncio(id, updatedAnuncio));
    }

    /**
     * <p>Endpoint para obter todos os anúncios cadastrados</p>
     * @param token Token de autenticação no HEADER
     * @return Lista dos anúncios cadastrados
     */
    @GetMapping("/anuncios")
    public List<AnuncioDTO> getAnuncios(@RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return anuncioService.getAnuncios().stream().map(AnuncioDTO::converterAnuncio).collect(Collectors.toList());
    }

    /**
     * <p>Endpoint para obter um anúncio pelo id</p>
     * @param id Identificador do anúncio no PATH
     * @param token Token de autenticação no HEADER
     * @return Objeto do anúncio que tem o id passado
     */
    @GetMapping("/anuncios/{id}")
    public AnuncioDTO getAnuncio(@PathVariable Long id, @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return AnuncioDTO.converterAnuncio(anuncioService.getAnuncioById(id));
    }

    /**
     * <p>Endpoint para deletar um anúncio pelo id</p>
     * @param id Identificador do anúncio no PATH
     * @param token Token de autenticação no HEADER
     */
    @DeleteMapping("/anuncios/{id}")
    public void deleteAnuncio(@PathVariable Long id, @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        anuncioService.deleteAnuncio(id);
    }

    /**
     * <p>Endpoint para adicionar usuário interessado no anúncio</p>
     * @param id Identificador do anúncio a ser adicionado usuario interessado no PATH
     * @param usuarioForm Objeto do usuario interessado no BODY
     * @param token Token de autenticação no HEADER
     * @return Objeto do anúncio atualizado
     */
    @PostMapping("/anuncios/interesse/{id}")
    public AnuncioDTO addInteresseAnuncio(@PathVariable Long id, @RequestBody UsuarioForm usuarioForm,
                                    @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        Usuario usuario = usuarioForm.toUsuario();
        return AnuncioDTO.converterAnuncio(anuncioService.addInteressado(id, usuario));
    }

    /**
     * <p>Endpoint para remover usuário interessado no anúncio</p>
     * @param id Identificador do anúncio a ser removido usuario interessado no PATH
     * @param usuarioForm Objeto do usuario a ser removido como interessado no BODY
     * @param token Token de autenticação no HEADER
     * @return Objeto do anúncio atualizado
     */
    @DeleteMapping("/anuncios/interesse/{id}")
    public AnuncioDTO deleteInteresseAnuncio(@PathVariable Long id, @RequestBody UsuarioForm usuarioForm,
                                    @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        Usuario usuario = usuarioForm.toUsuario();
        return AnuncioDTO.converterAnuncio(anuncioService.deleteInteressado(id, usuario));                 
    }
}
