package com.claudio.importcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CondicaoPagamentoDTO(
        @NotBlank(message = "A descrição da condição é obrigatória")
        String descricao,

        @NotNull(message = "Os dias de prazo são obrigatórios")
        @PositiveOrZero(message = "Os dias de prazo não podem ser negativos")
        Integer diasPrazo
) {}