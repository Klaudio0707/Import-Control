package com.claudio.importcontrol.controller;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProcessoImportacao>> listarTodos() {
        return ResponseEntity.ok(service.listar());
    }

   
    @PostMapping
    public ResponseEntity<ProcessoImportacao> criar(@RequestBody @Valid ProcessoDTO dados) {
   
        ProcessoImportacao processoSalvo = service.salvar(dados);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(processoSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessoImportacao> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessoImportacao> atualizar(@PathVariable String id, @RequestBody ProcessoDTO dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ProcessoImportacao>> filtrarPorFornecedor(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(service.buscarPorFornecedor(nome));
    }

    @GetMapping("/quantidade/{qtd}")
    public ResponseEntity<List<ProcessoImportacao>> filtrarPorQuantidade(@PathVariable Integer qtd) {
        return ResponseEntity.ok(service.buscarMaioresQue(qtd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
