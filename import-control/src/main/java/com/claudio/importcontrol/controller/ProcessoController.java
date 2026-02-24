package com.claudio.importcontrol.controller;

import java.util.List;

import com.claudio.importcontrol.dto.ProcessoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ProcessoController(@Autowired ProcessoService service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ProcessoResponseDTO>> listarTodos() {
        List<ProcessoResponseDTO> listaSegura = service.listar().stream()
                .map(ProcessoResponseDTO::new)
                .toList();
        return ResponseEntity.ok(listaSegura);
    }

    @PostMapping
    public ResponseEntity<ProcessoResponseDTO> criar(@RequestBody @Valid ProcessoDTO dados) {
        ProcessoImportacao processoSalvo = service.salvar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProcessoResponseDTO(processoSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessoResponseDTO> buscarPorId(@PathVariable String id) {
        ProcessoImportacao processo = service.buscarPorId(id);
        return ResponseEntity.ok(new ProcessoResponseDTO(processo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessoResponseDTO> atualizar(@PathVariable String id, @RequestBody ProcessoDTO dados) {
        ProcessoImportacao processoAtualizado = service.atualizar(id, dados);
        return ResponseEntity.ok(new ProcessoResponseDTO(processoAtualizado));
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ProcessoResponseDTO>> filtrarPorFornecedor(@RequestParam("nome") String nome) {
        List<ProcessoResponseDTO> lista = service.buscarPorFornecedor(nome).stream()
                .map(ProcessoResponseDTO::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/quantidade/{qtd}")
    public ResponseEntity<List<ProcessoResponseDTO>> filtrarPorQuantidade(@PathVariable Integer qtd) {
        List<ProcessoResponseDTO> lista = service.buscarMaioresQue(qtd).stream()
                .map(ProcessoResponseDTO::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
