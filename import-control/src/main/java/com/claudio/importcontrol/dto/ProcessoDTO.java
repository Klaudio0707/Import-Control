package com.claudio.importcontrol.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProcessoDTO(
        @NotBlank(message = "O número do processo é obrigatório.") String numeroProcesso,
        @NotBlank(message = "O identificador da invoice é obrigatório.") String identificadorInvoice,
        @NotBlank(message = "O fornecedor é obrigatório.") String fornecedor,
        @NotBlank(message = "O produto é obrigatório.") String produto,
        @NotNull(message = "A quantidade é obrigatória.") @Positive(message = "A quantidade deve ser maior que zero.") Double quantidade,
        @NotNull(message = "O preço é obrigatório.") @Positive(message = "O preço deve ser maior que zero.") BigDecimal preco,
        @NotNull(message = "A previsão de embarque é obrigatória.") LocalDate previsaoEmbarque,
        @NotBlank(message = "A unidade de medida é obrigatória.") String unidadeMedida,
        @NotBlank(message = "O status do processo é obrigatório.") String statusProcesso,
        @NotBlank(message = "O status do pagamento é obrigatório.") String statusPagamento,
        @NotNull(message = "O ID do usuário é obrigatório.") Long usuarioId,
        @Valid CondicaoPagamentoDTO condicaoPagamento,
        BigDecimal taxaCambio,
        LocalDate dataEmbarque,
        LocalDate dataChegada,
        String DI,
        Long condicaoPagamentoId
) {}