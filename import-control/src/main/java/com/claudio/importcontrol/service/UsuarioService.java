package com.claudio.importcontrol.service;

import com.claudio.importcontrol.dto.UsuarioResponseDTO;
import com.claudio.importcontrol.entity.Empresa;
import com.claudio.importcontrol.exception.EmailJaCadastradoException;
import com.claudio.importcontrol.exception.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.claudio.importcontrol.dto.UsuarioDTO;
import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criar(UsuarioDTO dados) {
        if (repository.existsByEmail(dados.email())) {
            throw new EmailJaCadastradoException("O e-mail " + dados.email() + " já está cadastrado.");
        }
        Empresa empresa = empresaService.salvarEmpresaPeloCnpj(dados.cnpj());

        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setEmpresa(empresa);
        usuario.setAcesso(dados.acesso());

        usuario.setSenha(passwordEncoder.encode(dados.senha()));

        return repository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado.")
        );
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }
}
