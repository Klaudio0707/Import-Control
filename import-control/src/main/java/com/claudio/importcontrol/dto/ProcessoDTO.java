package com.claudio.importcontrol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProcessoDTO(
     @NotBlank(message = "O ID do processo é obrigatório")
    String numeroProcesso,
    String identificadorInvoice,
    String fornecedor,
    String produto,
    Double quantidade,
    BigDecimal precoPorQuilo,
    LocalDate dataEmbarque,
    Long usuarioId
) {
    
    
}