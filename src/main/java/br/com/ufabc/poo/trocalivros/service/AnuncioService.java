package br.com.ufabc.poo.trocalivros.service;

import br.com.ufabc.poo.trocalivros.model.Anuncio;
import br.com.ufabc.poo.trocalivros.repository.AnuncioRepository;
import br.com.ufabc.poo.trocalivros.utils.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    @Transactional
    public Anuncio getAnuncioById(Long id) {
        Optional<Anuncio> possibleAnuncio = anuncioRepository.findById(id);
        UtilFunctions.not_found(possibleAnuncio.isPresent(), "Anuncio não encontrado.");
        return possibleAnuncio.get();
    }

    @Transactional
    public Anuncio createAnuncio(Anuncio newAnuncio) {
        return anuncioRepository.save(newAnuncio);
    }

    @Transactional
    public Anuncio updateAnuncio(Long id, Anuncio updatedAnuncio) {
        updatedAnuncio.setId(id);
        anuncioRepository.deleteById(id);
        return anuncioRepository.save(updatedAnuncio);
    }

    @Transactional
    public void deleteAnuncio(Long id) {
        anuncioRepository.deleteById(id);
    }

    @Transactional
    public List<Anuncio> getAnuncios() {
        return Streamable.of(anuncioRepository.findAll()).toList();
    }
}
