package com.claudio.importcontrol.dto;


import java.time.LocalDateTime;

public record EventoDTO(

    @NotBlank(message = "O ID do processo é obrigatório")
    String processoId, 

    @NotBlank(message = "Descrição é obrigatória")
    String descricao,

    @NotBlank(message = "Localização é obrigatória")
    String localizacao,
    
    @NotBlank(message = "Status é obrigatório")
    String status,

    // sem o NotBLank se torna opcional
    String rastreioDocumento

){   }
