package br.com.ufabc.poo.trocalivros.controller;

import br.com.ufabc.poo.trocalivros.dto.AnuncioDTO;
import br.com.ufabc.poo.trocalivros.model.Anuncio;
import br.com.ufabc.poo.trocalivros.service.AnuncioService;
import br.com.ufabc.poo.trocalivros.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        anuncioService.getAnuncioById(id);
        return AnuncioDTO.converterAnuncio(anuncioService.updateAnuncio(id, updatedAnuncio));
    }

    @GetMapping("/anuncios")
    public List<AnuncioDTO> getAnuncios(@RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        return anuncioService.getAnuncios().stream().map(AnuncioDTO::converterAnuncio).collect(Collectors.toList());
    }

    @DeleteMapping("/anuncios/{id}")
    public void deleteAnuncio(@PathVariable Long id, @RequestHeader(value="AuthToken") String token) {
        AutenticacaoService.verifyToken(token);
        anuncioService.getAnuncioById(id);
        anuncioService.deleteAnuncio(id);
    }
}
