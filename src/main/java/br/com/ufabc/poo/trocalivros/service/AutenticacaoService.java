package br.com.ufabc.poo.trocalivros.service;

import br.com.ufabc.poo.trocalivros.model.AuthToken;
import br.com.ufabc.poo.trocalivros.model.Usuario;
import br.com.ufabc.poo.trocalivros.repository.UsuarioRepository;
import br.com.ufabc.poo.trocalivros.utils.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutenticacaoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario getUserById(String username) {
        Optional<Usuario> possibleUser = usuarioRepository.findById(username);
        UtilFunctions.not_found(possibleUser.isPresent(), "Usuario não encontrado.");
        return possibleUser.get();
    }

    @Transactional
    public Usuario createUser(Usuario newUser) {
        newUser.setPassword(UtilFunctions.encode_password(newUser.getPassword()));
        return usuarioRepository.save(newUser);
    }

    @Transactional
    public Usuario updateUser(String username, Usuario updatedUser) {
        updatedUser.setUsername(username);
        updatedUser.setPassword(UtilFunctions.encode_password(updatedUser.getPassword()));
        usuarioRepository.deleteById(username);
        return usuarioRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUser(String username) {
        usuarioRepository.deleteById(username);
    }

    @Transactional
    public List<Usuario> getUsers() {
        return Streamable.of(usuarioRepository.findAll()).toList();
    }

    public static String authenticateUser(String encoded_password, String password) {
        UtilFunctions.unauthorized(encoded_password.equals(password), "Credenciais inválidas");
        return AuthToken.getToken();
    }

    public static void verifyToken(String token) {
            UtilFunctions.unauthorized(AuthToken.checkToken(token), "Token inválido");
    }
}
