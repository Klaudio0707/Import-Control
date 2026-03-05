package com.claudio.importcontrol.repository;

import com.claudio.importcontrol.entity.CondicaoPagamento;
import com.claudio.importcontrol.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CondicaoPagamentoRepository extends JpaRepository<CondicaoPagamento, Long> {
    Optional<CondicaoPagamento> findByDescricaoAndDiasPrazoAndUsuario(
            String descricao,
            Integer diasPrazo,
            Usuario usuario
    );

    List<CondicaoPagamento> findByUsuarioId(Long usuarioId);
}