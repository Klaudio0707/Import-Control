package com.claudio.importcontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.claudio.importcontrol.dto.UsuarioDTO;
import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.enums.UserRole;
import com.claudio.importcontrol.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario criar(UsuarioDTO dados) {
        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(dados.senha()); 
        usuario.setRole(UserRole.USER);

        //usarei o hash 
        try {
            return repository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O e-mail " + dados.email() + " já está cadastrado.");
        }
    }
     public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado.")
        );
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }
}