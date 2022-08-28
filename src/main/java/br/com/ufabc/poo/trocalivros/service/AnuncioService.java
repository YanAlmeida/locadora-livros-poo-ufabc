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

/**
 * Serviço referente às operações relacionadas aos Anúncios
 */
@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    /**
     * Método responsável por retornar anúncio recebendo um id
     * @param id Identificador do anúncio a ser retornado
     * @return Objeto do Anúncio encontrado na base de dados
     */
    @Transactional
    public Anuncio getAnuncioById(Long id) {
        Optional<Anuncio> possibleAnuncio = anuncioRepository.findById(id);
        UtilFunctions.not_found(possibleAnuncio.isPresent(), "Anuncio não encontrado.");
        return possibleAnuncio.get();
    }

    /**
     * Método responsável para criar um novo anúncio
     * @param newAnuncio Objeto do novo anúncio a ser criado
     * @return Objeto do anúncio recém adicionado na base de dados
     */
    @Transactional
    public Anuncio createAnuncio(Anuncio newAnuncio) {
        return anuncioRepository.save(newAnuncio);
    }

    /**
     * Método responsável para atualizar um anúncio
     * @param id Identificador do anúncio a ser atualizado 
     * @param updatedAnuncio Objeto com as novos valores das propriedades do anúncio
     * @return Objeto do anúncio atualizado 
     */
    @Transactional
    public Anuncio updateAnuncio(Long id, Anuncio updatedAnuncio) {
        return anuncioRepository.save(updatedAnuncio);
    }

    /**
     * Método responsável para excluir um anúncio
     * @param id Identificador do anúncio a ser removido da base
     */
    @Transactional
    public void deleteAnuncio(Long id) {
        anuncioRepository.deleteById(id);
    }

    /**
     * Método responsável por obter todos os anúncios cadastrados
     * @return Lista com todos os anúncios cadastrados
     */
    @Transactional
    public List<Anuncio> getAnuncios() {
        return Streamable.of(anuncioRepository.findAll()).toList();
    }

    /**
     * Método responsável por adicionar usuário interessado em um anúncio específico
     * @param id Identificador do anúncio a ser adicionado o usuário
     * @param usuario Objeto do Usuario a ser adicionado como usuário interessado
     * @return Objeto do anúncio atualizado após a inclusão do usuário
     */
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

    /**
     * Método responsável por remover usuário interessado em um anúncio específico
     * @param id Identificador do anúncio a ser removido o usuário
     * @param usuario Objeto do Usuario a ser removido como usuário interessado
     * @return Objeto do anúncio atualizado após a exclusão do usuário
     */
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
