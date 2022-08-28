package br.com.ufabc.poo.trocalivros.controller;

import br.com.ufabc.poo.trocalivros.dto.UsuarioDTO;
import br.com.ufabc.poo.trocalivros.dto.UsuarioForm;
import br.com.ufabc.poo.trocalivros.model.AuthUser;
import br.com.ufabc.poo.trocalivros.model.Usuario;
import br.com.ufabc.poo.trocalivros.service.AutenticacaoService;
import br.com.ufabc.poo.trocalivros.utils.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    /**
     * <p>Endpoint para realizar a autenticação de um usuário cadastrado</p>
     * @param userData Objeto AuthUser que recebe username e password no BODY
     * @return Token de autenticação
     */
    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody AuthUser userData){
        Usuario user = autenticacaoService.getUserById(userData.getUsername());
        String encoded_password = UtilFunctions.encode_password(userData.getPassword());
        return AutenticacaoService.authenticateUser(encoded_password, user.getPassword());
    }

    /**
     * <p>Endpoint para cadastrar novo usuário</p>
     * @param newUser Objeto de um novo UsuarioForm a ser cadastrado no BODY
     * @return Objeto do novo usuário
     */
    @PostMapping("/usuarios")
    public UsuarioDTO createUser(@RequestBody UsuarioForm newUser){
        return UsuarioDTO.converterUsuario(autenticacaoService.createUser(newUser.toUsuario()));
    }

    /**
     * <p>Endpoint para atualizar usuário pelo username</p>
     * @param username Username do usuário que vai ser atualizado no PATH
     * @param updatedUser Objeto com as novas informações do usuário a ser atualizado no BODY
     * @return Objeto do usuário atualizado
     */
    @PutMapping("/usuarios/{username}")
    public UsuarioDTO updateUser(@PathVariable String username, @RequestBody UsuarioForm updatedUser) {
        autenticacaoService.getUserById(username);
        return UsuarioDTO.converterUsuario(autenticacaoService.updateUser(username, updatedUser.toUsuario()));
    }

    /**
     * <p>Endpoint para obter todos os usuários cadastrados</p>
     * @return Lista de todos os usuários cadastrados
     */
    @GetMapping("/usuarios")
    public List<UsuarioDTO> getUsers() {
        return autenticacaoService.getUsers().stream().map(UsuarioDTO::converterUsuario).collect(Collectors.toList());
    }

    /**
     * <p>Endpoint para deletar um usuário cadastrado pelo username</p>
     * @param username Username do usuário a ser deletado no PATH
     */
    @DeleteMapping("/usuarios/{username}")
    public void deleteUser(@PathVariable String username) {
        autenticacaoService.getUserById(username);
        autenticacaoService.deleteUser(username);
    }
}
