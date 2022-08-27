package br.com.ufabc.poo.trocalivros.repository;

import br.com.ufabc.poo.trocalivros.model.Anuncio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends CrudRepository<Anuncio, Long> {
}
