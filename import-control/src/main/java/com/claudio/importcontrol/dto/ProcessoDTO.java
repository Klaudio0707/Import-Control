package com.claudio.importcontrol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProcessoDTO(
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