package com.claudio.importcontrol.service;

import com.claudio.importcontrol.entity.CondicaoPagamento;
import com.claudio.importcontrol.repository.CondicaoPagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondicaoPagamentoService {

    private final CondicaoPagamentoRepository repository;

    public CondicaoPagamentoService(CondicaoPagamentoRepository repository) {
        this.repository = repository;
    }

    public List<CondicaoPagamento> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public CondicaoPagamento salvar(CondicaoPagamento condicao) {
        //validações de negócio
        return repository.save(condicao);
    }
}