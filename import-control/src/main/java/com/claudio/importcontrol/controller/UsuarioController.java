package com.claudio.importcontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@RequestBody UsuarioDTO dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }
}