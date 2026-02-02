package com.claudio.importcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claudio.importcontrol.dto.ProcessoDTO;
import com.claudio.importcontrol.entity.ProcessoImportacao;
import com.claudio.importcontrol.service.ProcessoService;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService service;

    @GetMapping
    public ResponseEntity<List<ProcessoImportacao>> listarTodos() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<ProcessoImportacao> salvar(@RequestBody ProcessoDTO dados) {
        ProcessoImportacao salvo = service.criar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ProcessoImportacao buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProcessoImportacao atualizar(@PathVariable String id, @RequestBody ProcessoDTO dados) {
        return service.atualizar(id, dados);
    }

    @GetMapping("/filtro")
    public List<ProcessoImportacao> filtrarPorFornecedor(@RequestParam("nome") String nome) {
        return service.buscarPorFornecedor(nome);
    }

    @GetMapping("/quantidade/{qtd}")
    public List<ProcessoImportacao> filtrarPorQuantidade(@PathVariable Integer qtd) {
        return service.buscarMaioresQue(qtd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
