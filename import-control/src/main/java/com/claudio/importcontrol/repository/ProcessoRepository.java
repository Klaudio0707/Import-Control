package com.claudio.importcontrol.repository;

import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claudio.importcontrol.entity.ProcessoImportacao;

@Repository
public interface ProcessoRepository extends JpaRepository<ProcessoImportacao, String> {

    boolean existsByNumeroProcesso(String numeroProcesso);

    List<ProcessoImportacao> findByFornecedorContainingIgnoreCase(String fornecedor);

    @Query("SELECT p FROM ProcessoImportacao p WHERE p.quantidade >= :qtd")
    List<ProcessoImportacao> buscarAcimaDe(@Param("qtd") Integer qtd);

}