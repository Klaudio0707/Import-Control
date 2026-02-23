package com.claudio.importcontrol.repository;

import com.claudio.importcontrol.entity.Empresa;
import com.claudio.importcontrol.entity.EventoRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<EventoRastreio> findByProcessoId(String processoId);

}