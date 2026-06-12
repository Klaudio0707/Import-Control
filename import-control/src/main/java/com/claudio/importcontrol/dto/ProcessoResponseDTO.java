package com.claudio.importcontrol.dto;

import com.claudio.importcontrol.entity.ProcessoImportacao;
import java.math.BigDecimal;

public record ProcessoResponseDTO(
        String id,
        String numeroProcesso,
        String identificadorInvoice,
        String fornecedor,
        String produto,
        Double quantidade,
        String unidadeMedida,
        BigDecimal preco,
        BigDecimal valorTotalReal,
        BigDecimal taxaCambio,
        BigDecimal valorTotal,
        String statusLogistico,
        String statusFinanceiro,
        String statusPrazo,
        String nomeUsuarioResponsavel,
        String razaoSocialEmpresa,
        String condicaoPagamento
) {
    public ProcessoResponseDTO(ProcessoImportacao processo) {
        this(
                processo.getId(),
                processo.getNumeroProcesso(),
                processo.getIdentificadorInvoice(),
                processo.getFornecedor(),
                processo.getProduto(),
                processo.getQuantidade(),
                processo.getUnidadeMedida() != null ? processo.getUnidadeMedida().name() : "-",
                processo.getPreco(),
                processo.getValorTotalReal(),
                processo.getTaxaCambio(),
                processo.getValorTotal(),
                processo.getStatusLogistico() != null ? processo.getStatusLogistico().name() : "INDEFINIDO",
                processo.getStatusFinanceiro() != null ? processo.getStatusFinanceiro().name() : "PENDENTE",


                processo.getStatusPrazo(),

                processo.getUsuario() != null ? processo.getUsuario().getNome() : "Sem Responsável",
                processo.getUsuario() != null && processo.getUsuario().getEmpresa() != null
                        ? processo.getUsuario().getEmpresa().getRazaoSocial()
                        : "Sem Empresa",


                processo.getCondicaoPagamento() != null ? processo.getCondicaoPagamento().getDescricao() : "Não Informada"
        );
    }


}