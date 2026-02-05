package com.claudio.importcontrol.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.claudio.importcontrol.entity.EventoRastreio;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepository extends JpaRepository<EventoRastreio, Long> {  
}