package com.claudio.importcontrol.dto;
import com.claudio.importcontrol.enums.UserRole;

public record UsuarioDTO(
    String nome,
    String email,
    String senha,
    UserRole role
) {}
