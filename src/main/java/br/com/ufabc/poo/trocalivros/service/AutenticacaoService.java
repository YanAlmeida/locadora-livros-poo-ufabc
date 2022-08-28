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

/**
 * Serviço referente a parte de autenticação do Usuário
 */
@Service
public class AutenticacaoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Método responsável por obter usuário a partir do username
     * @param username Texto do username do usuário
     * @return Objeto do usuário que possui o username passado
     */
    @Transactional
    public Usuario getUserById(String username) {
        Optional<Usuario> possibleUser = usuarioRepository.findById(username);
        UtilFunctions.not_found(possibleUser.isPresent(), "Usuario não encontrado.");
        return possibleUser.get();
    }

    /**
     * Método responsável por cadastrar novo usuário
     * @param newUser Objeto do usuário a ser cadastrado
     * @return Objeto do usuário cadastrado na base
     */
    @Transactional
    public Usuario createUser(Usuario newUser) {
        newUser.setPassword(UtilFunctions.encode_password(newUser.getPassword()));
        return usuarioRepository.save(newUser);
    }

    /**
     * Método responsável por atualizar usuário
     * @param username Identificador do usuário a ser atualizado
     * @param updatedUser Objeto com os novos valores a serem atualizados
     * @return Objeto do usuário atualizado
     */
    @Transactional
    public Usuario updateUser(String username, Usuario updatedUser) {
        updatedUser.setUsername(username);
        updatedUser.setPassword(UtilFunctions.encode_password(updatedUser.getPassword()));
        usuarioRepository.deleteById(username);
        return usuarioRepository.save(updatedUser);
    }

    /**
     * Método responsável por excluir usuário pelo username
     * @param username Identificador do usuário a ser excluído
     */
    @Transactional
    public void deleteUser(String username) {
        usuarioRepository.deleteById(username);
    }

    /**
     * Método responsável por retornar todos usuários cadastrados
     * @return Lista de todos os usuários cadastrados
     */
    @Transactional
    public List<Usuario> getUsers() {
        return Streamable.of(usuarioRepository.findAll()).toList();
    }

    /**
     * Método responsável por retornar token de autenticação
     * @param encoded_password Texto da senha criptografado
     * @param password Texto da senha criptografada armazenada na base
     * @return Token de autenticação
     */
    public static String authenticateUser(String encoded_password, String password) {
        UtilFunctions.unauthorized(encoded_password.equals(password), "Credenciais inválidas");
        return AuthToken.getToken();
    }

    /**
     * Método responsável por verificar validade do token de autenticação
     * @param token Token de autenticação a ser validado
     */
    public static void verifyToken(String token) {
            UtilFunctions.unauthorized(AuthToken.checkToken(token), "Token inválido");
    }
}
