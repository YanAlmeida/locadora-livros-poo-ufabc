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
    

    @PostMapping("/anuncios")
    public AnuncioDTO createAnuncio(@RequestBody Anuncio newAnuncio, @RequestHeader(value="AuthToken") String token){
        AutenticacaoService.verifyToken(token);
        return AnuncioDTO.converterAnuncio(anuncioService.createAnuncio(newAnuncio));
    }

    @PutMapping("/anuncios/{id}")
    public AnuncioDTO updateAnuncio(@PathVariable Long id, @RequestBody Anuncio updatedAnuncio,
                                    @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return AnuncioDTO.converterAnuncio(anuncioService.updateAnuncio(id, updatedAnuncio));
    }

    @GetMapping("/anuncios")
    public List<AnuncioDTO> getAnuncios(@RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return anuncioService.getAnuncios().stream().map(AnuncioDTO::converterAnuncio).collect(Collectors.toList());
    }

    @GetMapping("/anuncios/{id}")
    public AnuncioDTO getAnuncio(@PathVariable Long id, @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return AnuncioDTO.converterAnuncio(anuncioService.getAnuncioById(id));
    }

    @DeleteMapping("/anuncios/{id}")
    public void deleteAnuncio(@PathVariable Long id, @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        anuncioService.deleteAnuncio(id);
    }

    @PostMapping("/anuncios/interesse/{id}")
    public AnuncioDTO addInteresseAnuncio(@PathVariable Long id, @RequestBody UsuarioForm usuarioInteressado,
                                    @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        Usuario usuario = usuarioInteressado.toUsuario();
        return AnuncioDTO.converterAnuncio(anuncioService.addInteressado(id, usuario));
    }

    @DeleteMapping("/anuncios/interesse/{id}")
    public AnuncioDTO deleteInteresseAnuncio(@PathVariable Long id, @RequestBody UsuarioForm usuarioDesinteressado,
                                    @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        Usuario usuario = usuarioDesinteressado.toUsuario();
        return AnuncioDTO.converterAnuncio(anuncioService.deleteInteressado(id, usuario));                 
    }
}
