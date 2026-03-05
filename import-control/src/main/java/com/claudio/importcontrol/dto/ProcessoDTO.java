package com.claudio.importcontrol.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProcessoDTO(
        @NotBlank String numeroProcesso,
        @NotBlank String identificadorInvoice,
        @NotBlank String fornecedor,
        @NotBlank String produto,
        @NotNull @Positive Double quantidade,
        @NotNull @Positive BigDecimal preco,
        @NotNull LocalDate previsaoEmbarque,
        @NotBlank String unidadeMedida,
        @NotBlank String statusProcesso,
        @NotBlank String statusPagamento,
        @NotNull Long usuarioId,


        @Valid CondicaoPagamentoDTO condicaoPagamento,


        BigDecimal taxaCambio,
        LocalDate dataEmbarque,
        LocalDate dataChegada,
        String DI,
        Long condicaoPagamentoId
) {}