package com.claudio.importcontrol.repository;

import com.claudio.importcontrol.entity.Empresa;
import com.claudio.importcontrol.entity.EventoRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCnpj(String cnpj);

}