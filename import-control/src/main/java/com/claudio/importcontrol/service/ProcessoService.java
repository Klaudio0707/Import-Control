package com.claudio.importcontrol.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.claudio.importcontrol.dto.ProcessoDTO;
import com.claudio.importcontrol.entity.ProcessoImportacao;
import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.repository.ProcessoRepository;
import com.claudio.importcontrol.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ProcessoImportacao> listar() {
        List<ProcessoImportacao> processos = repository.findAll();
        if (processos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum processo encontrado.");
        }
        return processos;
    }

    @Transactional 
    public ProcessoImportacao salvar(ProcessoDTO dados) {
        
    
        ProcessoImportacao processo = new ProcessoImportacao();
        
        processo.setNumeroProcesso(dados.numeroProcesso());
        processo.setIdentificadorInvoice(dados.identificadorInvoice());
        processo.setFornecedor(dados.fornecedor());
        processo.setProduto(dados.produto());
        processo.setQuantidade(dados.quantidade());
        processo.setPrecoPorQuilo(dados.precoPorQuilo());
        processo.setUnidadeMedida(dados.unidadeMedida());

        // Datas e Prazos
        processo.setPrevisaoEmbarque(dados.previsaoEmbarque());
        processo.setDataEmbarque(dados.dataEmbarque());
        processo.setDiasParaPagamento(dados.diasParaPagamento());

        // Enums
        processo.setStatusLogistico(dados.statusProcesso());
        processo.setStatusFinanceiro(dados.statusPagamento());
        processo.setFormaPagamento(dados.formaPagamento());
        processo.setDI(dados.DI());

        
        if (dados.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dados.usuarioId()));
            processo.setUsuario(usuario);
        }

        return repository.save(processo);
    }

    public ProcessoImportacao atualizar(String id, ProcessoDTO dados) {
        ProcessoImportacao processoExistente = buscarPorId(id);

        if (!processoExistente.getNumeroProcesso().equals(dados.numeroProcesso())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é permitido alterar o Número do Processo.");
        }

        processoExistente.setIdentificadorInvoice(dados.identificadorInvoice());
        processoExistente.setFornecedor(dados.fornecedor());
        processoExistente.setProduto(dados.produto());
        processoExistente.setQuantidade(dados.quantidade());
        processoExistente.setPrecoPorQuilo(dados.precoPorQuilo());
        processoExistente.setDataEmbarque(dados.dataEmbarque());

        return repository.save(processoExistente);
    }

    public List<ProcessoImportacao> buscarPorFornecedor(String nome) {
        return repository.findByFornecedorContainingIgnoreCase(nome);
    }

    public List<ProcessoImportacao> buscarMaioresQue(Integer quantidade) {
        return repository.buscarAcimaDe(quantidade);
    }

    public ProcessoImportacao buscarPorId(String id) {
        return repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo com ID " + id + " não encontrado.")
        );
    }

    public void excluir(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
