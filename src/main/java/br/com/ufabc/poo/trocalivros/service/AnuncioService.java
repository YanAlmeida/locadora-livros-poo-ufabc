package br.com.ufabc.poo.trocalivros.service;

import br.com.ufabc.poo.trocalivros.dto.UsuarioForm;
import br.com.ufabc.poo.trocalivros.model.Anuncio;
import br.com.ufabc.poo.trocalivros.model.Usuario;
import br.com.ufabc.poo.trocalivros.repository.AnuncioRepository;
import br.com.ufabc.poo.trocalivros.utils.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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

    @Transactional
    public Anuncio addInteressado(Long id, Usuario usuario){
        Anuncio anuncio = getAnuncioById(id);
        if(anuncio.getUsuariosInteressados() != null){
            List<Usuario> todosUsuariosInteressados = anuncio.getUsuariosInteressados();
            todosUsuariosInteressados.add(usuario);
            anuncio.setUsuariosInteressados(todosUsuariosInteressados);
        }else{
            List<Usuario> novaListaInteressados = new ArrayList<Usuario>();
            novaListaInteressados.add(usuario);
            anuncio.setUsuariosInteressados(novaListaInteressados);
        }
        return anuncioRepository.save(anuncio);
    }

    @Transactional
    public Anuncio deleteInteressado(Long id, Usuario usuario){
        Anuncio anuncio = getAnuncioById(id);
        //verifica se realmente esse cara ta interessado no anuncio
        if(anuncio.getUsuariosInteressados() != null){
            List<Usuario> listaUsuariosInteressado = anuncio.getUsuariosInteressados();
            Optional<Usuario> usuarioEstaInteressado = listaUsuariosInteressado.stream().filter(usr -> usr.getUsername().equals(usuario.getUsername())).findAny();
            if(usuarioEstaInteressado != null){
                listaUsuariosInteressado.removeIf(u -> u.getUsername().equals(usuario.getUsername()));
                anuncio.setUsuariosInteressados(listaUsuariosInteressado);
            }
        }
        return anuncioRepository.save(anuncio);
    }
}
