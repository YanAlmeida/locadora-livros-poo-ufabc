package br.com.ufabc.poo.trocalivros.repository;

import br.com.ufabc.poo.trocalivros.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
}
