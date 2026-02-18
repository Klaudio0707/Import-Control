package com.claudio.importcontrol.controller;



import com.claudio.importcontrol.dto.LoginDTO;
import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.repository.UsuarioRepository;
import com.claudio.importcontrol.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dados) {
        

        Optional<Usuario> usuarioOptional = repository.findByEmail(dados.email());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (passwordEncoder.matches(dados.senha(), usuario.getSenha())) {
                
                String token = tokenService.gerarToken(usuario);
                return ResponseEntity.ok().body(token);
            }
        }

        return ResponseEntity.status(401).build();
    }
}