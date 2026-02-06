package com.claudio.importcontrol.dto;

import jakarta.validation.constraints.NotBlank;

public record EventoDTO(

    @NotBlank(message = "ID do processo é obrigatório")
    String processoId, 
    @NotBlank(message = "Descrição do evento é obrigatória")
    String descricao,
    @NotBlank(message = "A localização é obrigatória")
    String localizacao,
    @NotBlank(message = "O status é obrigatório")
    String status,

    String rastreioDocumento

){   }
