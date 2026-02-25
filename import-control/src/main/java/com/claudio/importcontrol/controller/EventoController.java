package com.claudio.importcontrol.controller;

import com.claudio.importcontrol.dto.EventoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.claudio.importcontrol.dto.EventoDTO;
import com.claudio.importcontrol.service.EventoService;

import java.util.List;

@RestController 
@RequestMapping("/eventos") 
public class EventoController {

    private final EventoService service; 

    public EventoController(EventoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> adicionarEventoManual(@RequestBody @Valid EventoDTO dados) {
        service.registrarManual(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("/processo/{processoId}")
    public ResponseEntity<List<EventoResponseDTO>> buscarTimeline(@PathVariable String processoId) {
        return ResponseEntity.ok(service.listarHistoricoDoProcesso(processoId));
    }
    
}