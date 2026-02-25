package com.claudio.importcontrol.controller;

import com.claudio.importcontrol.entity.CondicaoPagamento;
import com.claudio.importcontrol.repository.CondicaoPagamentoRepository;
import com.claudio.importcontrol.service.CondicaoPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condicoes/pagamento")
public class CondicaoPagamentoController {

    private final CondicaoPagamentoService service;

    public CondicaoPagamentoController(CondicaoPagamentoService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CondicaoPagamento>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<CondicaoPagamento> criar(@RequestBody CondicaoPagamento condicao) {
        return ResponseEntity.ok(service.salvar(condicao));
    }
}