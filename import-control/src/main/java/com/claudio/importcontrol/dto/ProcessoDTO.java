package com.claudio.importcontrol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.claudio.importcontrol.enums.FormaPagamento;
import com.claudio.importcontrol.enums.StatusPagamento;
import com.claudio.importcontrol.enums.StatusProcesso;
import com.claudio.importcontrol.enums.UnidadeMedida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProcessoDTO(

     @NotBlank(message = "Numero do processo é obrigatório")
    String numeroProcesso,
     @NotBlank(message = "Identificador do invoice é obrigatório")
    String identificadorInvoice,
     @NotBlank(message = "Fornecedor é obrigatório")
    String fornecedor,
     @NotBlank(message = "Produto é obrigatório")
    String produto,
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    Double quantidade,
    @NotNull(message = "Unidade de medida é obrigatória")
    UnidadeMedida unidadeMedida,
    @NotNull(message = "Preço por quilo é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    BigDecimal precoPorQuilo,
    @NotNull(message = "Previsão de embarque é obrigatória")
    LocalDate previsaoEmbarque,
    
    LocalDate dataEmbarque,
    LocalDate dataChegada,

    LocalDate dataVencimento,
    LocalDate dataPagamento, 
    
   @NotNull(message = "Status do processo é obrigatório")
    StatusProcesso statusProcesso, 

    @NotNull(message = "Status do pagamento é obrigatório")
    StatusPagamento statusPagamento, 

    @NotNull(message = "Forma de pagamento é obrigatória")
    FormaPagamento formaPagamento,
    
    Integer diasParaPagamento, 

    String DI,
   
    @NotNull(message = "O ID do usuário responsável é obrigatório")
    Long usuarioId
) {
    
    
}