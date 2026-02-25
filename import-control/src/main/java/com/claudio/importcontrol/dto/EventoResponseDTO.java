package com.claudio.importcontrol.dto;

import com.claudio.importcontrol.entity.EventoRastreio;

import java.time.LocalDateTime;

public record EventoResponseDTO(
        Long id,
        String descricao,
        String localizacao,
        String status,
        String rastreioDocumento,
        LocalDateTime dataEvento
) {
    public EventoResponseDTO(EventoRastreio evento) {
        this(
                evento.getId(),
                evento.getDescricao(),
                evento.getLocalizacao(),
                evento.getStatus(),
                evento.getRastreioDocumento(),
                evento.getDataEvento()
        );
    }
}
