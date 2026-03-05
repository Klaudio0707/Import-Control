package com.claudio.importcontrol.dto;

import com.claudio.importcontrol.entity.ProcessoImportacao;
import java.math.BigDecimal;

public record ProcessoResponseDTO(
        String id,
        String numeroProcesso,
        String fornecedor,
        String produto,
        BigDecimal valorTotal,
        String statusLogistico,
        String statusPrazo,
        String nomeUsuarioResponsavel,
        String razaoSocialEmpresa
) {
    public ProcessoResponseDTO(ProcessoImportacao processo) {
        this(
                processo.getId(),
                processo.getNumeroProcesso(),
                processo.getFornecedor(),
                processo.getProduto(),
                processo.getValorTotal(),
                processo.getStatusLogistico() != null ? processo.getStatusLogistico().name() : "INDEFINIDO",
                processo.getStatusFinanceiro() != null ? processo.getStatusFinanceiro().name() : "INDEFINIDO",
                processo.getUsuario() != null ? processo.getUsuario().getNome() : "Sem Responsável",

                processo.getUsuario() != null && processo.getUsuario().getEmpresa() != null
                        ? processo.getUsuario().getEmpresa().getRazaoSocial()
                        : "Sem Empresa"
        );
    }
}