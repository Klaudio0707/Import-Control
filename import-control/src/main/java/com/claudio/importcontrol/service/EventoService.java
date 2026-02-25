package com.claudio.importcontrol.service;

import java.util.List;

import com.claudio.importcontrol.dto.EventoResponseDTO;
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

    private final EventoRepository eventoRepository;

    private final ProcessoRepository processoRepository;

    public EventoService(EventoRepository eventoRepository,ProcessoRepository processoRepository ){
        this.eventoRepository = eventoRepository;
        this.processoRepository = processoRepository;
    }
    public void registrarManual(EventoDTO dados) {
        registrarInterno(dados.processoId(), dados.descricao(), dados.localizacao(), dados.status(), dados.rastreioDocumento());
    }
    public void registrarAutomatico(String processoId, String statusNovo, String descricao) {
        registrarInterno(processoId, descricao, "Sistema ImportControl", statusNovo, null);
    }

    private void registrarInterno(String processoId, String descricao, String localizacao, String status, String doc) {
        ProcessoImportacao processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não encontrado. ID: " + processoId));

        EventoRastreio evento = new EventoRastreio();
        evento.setProcesso(processo);
        evento.setDescricao(descricao);
        evento.setLocalizacao(localizacao != null ? localizacao : "N/A");
        evento.setStatus(status);
        evento.setRastreioDocumento(doc);

        eventoRepository.save(evento);
    }



    public List<EventoResponseDTO> listarHistoricoDoProcesso(String processoId) {
        return eventoRepository.findByProcessoIdOrderByDataEventoDesc(processoId)
                .stream()
                .map(EventoResponseDTO::new)
                .toList();
    }
}