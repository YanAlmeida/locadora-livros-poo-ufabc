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

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody AuthUser userData){
        Usuario user = autenticacaoService.getUserById(userData.getUsername());
        String encoded_password = UtilFunctions.encode_password(userData.getPassword());
        return AutenticacaoService.authenticateUser(encoded_password, user.getPassword());
    }

    @PostMapping("/usuarios")
    public UsuarioDTO createUser(@RequestBody UsuarioForm newUser){
        return UsuarioDTO.converterUsuario(autenticacaoService.createUser(newUser.toUsuario()));
    }

    @PutMapping("/usuarios/{username}")
    public UsuarioDTO updateUser(@PathVariable String username, @RequestBody UsuarioForm updatedUser) {
        autenticacaoService.getUserById(username);
        return UsuarioDTO.converterUsuario(autenticacaoService.updateUser(username, updatedUser.toUsuario()));
    }

    @GetMapping("/usuarios")
    public List<UsuarioDTO> getUsers() {
        return autenticacaoService.getUsers().stream().map(UsuarioDTO::converterUsuario).collect(Collectors.toList());
    }

    @DeleteMapping("/usuarios/{username}")
    public void deleteUser(@PathVariable String username) {
        autenticacaoService.getUserById(username);
        autenticacaoService.deleteUser(username);
    }
}
