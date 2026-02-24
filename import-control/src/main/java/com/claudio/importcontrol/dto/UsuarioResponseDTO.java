package com.claudio.importcontrol.dto;

import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.enums.PerfisAcesso;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        PerfisAcesso acesso,
        String razaoSocialEmpresa,
        String cnpjEmpresa
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getAcesso(),
                usuario.getEmpresa() != null ? usuario.getEmpresa().getRazaoSocial() : "Empresa não vinculada",
                usuario.getEmpresa() != null ? usuario.getEmpresa().getCnpj() : "N/A"
        );
    }
}