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

  public ProcessoImportacao criar(ProcessoDTO dados) {
        if (dados.usuarioId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do usuário é obrigatório.");
        }
        
        Usuario usuario = usuarioRepository.findById(dados.usuarioId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        if (dados.numeroProcesso() == null || dados.identificadorInvoice() == null || dados.fornecedor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios faltando.");
        }

        if (repository.existsByNumeroProcesso(dados.numeroProcesso())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "DUPLICIDADE: O processo " + dados.numeroProcesso() + " já existe no sistema."
            );
        }

        ProcessoImportacao novoProcesso = new ProcessoImportacao();
        novoProcesso.setUsuario(usuario); // VINCULA QUEM CADASTROU!
        novoProcesso.setNumeroProcesso(dados.numeroProcesso());
        novoProcesso.setIdentificadorInvoice(dados.identificadorInvoice());
        novoProcesso.setFornecedor(dados.fornecedor());
        novoProcesso.setProduto(dados.produto());
        novoProcesso.setQuantidade(dados.quantidade());
        novoProcesso.setPrecoPorQuilo(dados.precoPorQuilo());
        novoProcesso.setDataEmbarque(dados.dataEmbarque());

        return repository.save(novoProcesso);
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
