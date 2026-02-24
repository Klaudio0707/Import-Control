package com.claudio.importcontrol.service;

import com.claudio.importcontrol.dto.UsuarioResponseDTO;
import com.claudio.importcontrol.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        // 1. Busca ou cria a empresa
        Empresa empresa = empresaService.salvarEmpresaPeloCnpj(dados.cnpj());

        // 2. Cria o usuário de forma limpa, sem duplicações
        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setEmpresa(empresa);
        usuario.setAcesso(dados.acesso());

        // Criptografa e seta a senha apenas UMA vez
        usuario.setSenha(passwordEncoder.encode(dados.senha()));

        try {
            return repository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O e-mail " + dados.email() + " já está cadastrado.");
        }
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado.")
        );
    }

    // AQUI ESTÁ A MÁGICA: Uso de Streams e Lambdas exigido pelo Tech Lead!
    // Ele vai no banco, pega as Entidades, converte cada uma para DTO e devolve a lista segura.
    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .toList(); // .toList() é nativo do Java 16+
    }
}
