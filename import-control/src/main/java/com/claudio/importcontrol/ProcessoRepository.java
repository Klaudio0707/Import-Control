package com.claudio.importcontrol;


import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<ProcessoImportacao, String> {

    boolean existsByNumeroProcesso(String numeroProcesso);
}