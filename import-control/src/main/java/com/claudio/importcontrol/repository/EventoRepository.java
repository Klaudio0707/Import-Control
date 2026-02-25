package com.claudio.importcontrol.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.claudio.importcontrol.entity.EventoRastreio;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<EventoRastreio, Long> {
    List<EventoRastreio> findByProcessoIdOrderByDataEventoDesc(String processoId);
    
}