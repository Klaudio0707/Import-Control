package com.claudio.importcontrol.service;

import com.claudio.importcontrol.entity.CondicaoPagamento;
import com.claudio.importcontrol.entity.Usuario;
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

    public CondicaoPagamento buscarOuCriar(String descricao, Integer diasPrazo, Usuario usuario) {
        return repository.findByDescricaoAndDiasPrazoAndUsuario(descricao, diasPrazo, usuario)
                .orElseGet(() -> {
                    System.out.println("Criando nova condição: " + descricao);
                    CondicaoPagamento nova = new CondicaoPagamento(descricao, diasPrazo, usuario);
                    return repository.save(nova);
                });
    }

    public CondicaoPagamento salvar(CondicaoPagamento condicao) {
        return buscarOuCriar(condicao.getDescricao(), condicao.getDiasPrazo(), condicao.getUsuario());
    }
}