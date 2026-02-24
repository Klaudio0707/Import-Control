package com.claudio.importcontrol.controller;

import com.claudio.importcontrol.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.claudio.importcontrol.dto.UsuarioDTO;
import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody @Valid UsuarioDTO dados) {

        Usuario usuarioSalvo = service.criar(dados);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponseDTO(usuarioSalvo));
    }
        @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}