package com.claudio.importcontrol.controller;

import java.util.List;

import com.claudio.importcontrol.dto.ProcessoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.claudio.importcontrol.dto.ProcessoDTO;
import com.claudio.importcontrol.entity.ProcessoImportacao;
import com.claudio.importcontrol.service.ProcessoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoService service;

    public ProcessoController(ProcessoService service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ProcessoResponseDTO>> listarTodos() {
        // Como a service já devolve a lista de DTOs mapeada, é só repassar!
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<ProcessoResponseDTO> criar(@RequestBody @Valid ProcessoDTO dados) {
        ProcessoImportacao processoSalvo = service.salvar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProcessoResponseDTO(processoSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessoResponseDTO> buscarPorId(@PathVariable String id) {
        // Já devolve o DTO direto
        return ResponseEntity.ok(service.buscarPorIdDTO(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessoResponseDTO> atualizar(@PathVariable String id, @RequestBody ProcessoDTO dados) {
        ProcessoImportacao processoAtualizado = service.atualizar(id, dados);
        return ResponseEntity.ok(new ProcessoResponseDTO(processoAtualizado));
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ProcessoResponseDTO>> filtrarPorFornecedor(@RequestParam("nome") String nome) {
        // Já devolve a lista de DTOs pronta
        return ResponseEntity.ok(service.buscarPorFornecedor(nome));
    }

    @GetMapping("/quantidade/{qtd}")
    public ResponseEntity<List<ProcessoResponseDTO>> filtrarPorQuantidade(@PathVariable Double qtd) {
        // Já devolve a lista de DTOs pronta
        return ResponseEntity.ok(service.buscarMaioresQue(qtd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}