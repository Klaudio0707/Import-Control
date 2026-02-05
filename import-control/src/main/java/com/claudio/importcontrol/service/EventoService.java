package com.claudio.importcontrol.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.claudio.importcontrol.dto.EventoDTO;
import com.claudio.importcontrol.entity.EventoRastreio;
import com.claudio.importcontrol.entity.ProcessoImportacao;
import com.claudio.importcontrol.repository.EventoRepository;
import com.claudio.importcontrol.repository.ProcessoRepository;

@Service 
public class EventoService {

    @Autowired
    private EventoRepository repository; 

    @Autowired
    private ProcessoRepository processoRepository; 

    public EventoRastreio registrar(EventoDTO dados) {
       
        ProcessoImportacao processoPai = processoRepository.findById(dados.processoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não encontrado com ID: " + dados.processoId()));

        EventoRastreio evento = new EventoRastreio();
        evento.setDescricao(dados.descricao());
        evento.setLocalizacao(dados.localizacao());
        evento.setStatus(dados.status());
        
        evento.setRastreioDocumento(dados.rastreioDocumento()); 
        
        evento.setDataOcorrencia(LocalDateTime.now());

        evento.setProcesso(processoPai); 

        return repository.save(evento);
    }
}