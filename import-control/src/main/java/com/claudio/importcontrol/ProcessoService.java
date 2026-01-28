package com.claudio.importcontrol;

import com.claudio.importcontrol.dto.ProcessoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository repository;

    public List<ProcessoImportacao> listar() {
        return repository.findAll();
    }

    public ProcessoImportacao criar(ProcessoDTO dados) {
        ProcessoImportacao novoProcesso = new ProcessoImportacao();

        if (dados.numeroProcesso() == null || dados.identificadorInvoice() == null || dados.fornecedor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios faltando.");
        }
        if (repository.existsByNumeroProcesso(dados.numeroProcesso())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "DUPLICIDADE: O processo " + dados.numeroProcesso() + " já existe no sistema."
            );
        }

        novoProcesso.setNumeroProcesso(dados.numeroProcesso());
        novoProcesso.setIdentificadorInvoice(dados.identificadorInvoice());
        novoProcesso.setFornecedor(dados.fornecedor());
        novoProcesso.setProduto(dados.produto());
        novoProcesso.setQuantidade(dados.quantidade());
        novoProcesso.setPrecoPorQuilo(dados.precoPorQuilo());
        novoProcesso.setDataEmbarque(dados.dataEmbarque());

        return repository.save(novoProcesso);
    }

    public Optional<ProcessoImportacao> buscarPorId(String id) {
        return repository.findById(id);
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }
}
