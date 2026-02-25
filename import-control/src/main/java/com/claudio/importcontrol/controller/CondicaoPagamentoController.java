package com.claudio.importcontrol.controller;

import com.claudio.importcontrol.entity.CondicaoPagamento;
import com.claudio.importcontrol.repository.CondicaoPagamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condicoes/pagamento")
public class CondicaoPagamentoController {

    private final CondicaoPagamentoRepository repository;

    public CondicaoPagamentoController(CondicaoPagamentoRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CondicaoPagamento>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(repository.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<CondicaoPagamento> criar(@RequestBody CondicaoPagamento condicao) {
        return ResponseEntity.ok(repository.save(condicao));
    }
}