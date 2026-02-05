package com.claudio.importcontrol.dto;
import com.claudio.importcontrol.enums.PerfisAcesso;

public record UsuarioDTO(
    String nome,
    String email,
    String senha,
    PerfisAcesso role
) {}
