package com.claudio.importcontrol.dto;
import com.claudio.importcontrol.enums.PerfisAcesso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UsuarioDTO(

    @NotBlank(message = "O nome é obrigatório")
    String nome,
    @NotBlank(message = "O email é obrigatório")
    String email,
    @NotBlank(message = "A senha é obrigatória")
    String senha,
    @NotNull(message = "O perfil de acesso é obrigatório")
    PerfisAcesso acesso
) {}
