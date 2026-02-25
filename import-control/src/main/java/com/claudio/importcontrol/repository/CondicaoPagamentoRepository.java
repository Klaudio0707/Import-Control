package com.claudio.importcontrol.repository;

import com.claudio.importcontrol.entity.CondicaoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondicaoPagamentoRepository extends JpaRepository<CondicaoPagamento, Long> {
    List<CondicaoPagamento> findByUsuarioId(Long usuarioId);

}