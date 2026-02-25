package com.claudio.importcontrol.service;

import com.claudio.importcontrol.entity.Empresa;
import com.claudio.importcontrol.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final CnpjService cnpjService;

    public EmpresaService(EmpresaRepository empresaRepository, CnpjService cnpjService) {
        this.empresaRepository = empresaRepository;
        this.cnpjService = cnpjService;
    }

    @Transactional
    public Empresa salvarEmpresaPeloCnpj(String cnpjDigitado) {
        String cnpjLimpo = cnpjDigitado.replaceAll("\\D", "");

        return empresaRepository.findByCnpj(cnpjLimpo)
                .orElseGet(() -> {
                    System.out.println("CNPJ não encontrado localmente. Consultando BrasilAPI...");
                    var dados = cnpjService.consultarCnpj(cnpjLimpo);
                    boolean isAtiva = "ATIVA".equalsIgnoreCase(dados.situacaoCadastral());
                    if (!isAtiva) {
                        throw new RuntimeException("Bloqueio de Cadastro: Empresa possui situação: " + dados.situacaoCadastral());
                    }

                    Empresa novaEmpresa = new Empresa();
                    novaEmpresa.setCnpj(cnpjLimpo);
                    novaEmpresa.setRazaoSocial(dados.razaoSocial());
                    novaEmpresa.setNomeFantasia(dados.nomeFantasia());
                    novaEmpresa.setMunicipio(dados.municipio());
                    novaEmpresa.setUf(dados.uf());
                    novaEmpresa.setActive(true);

                    return empresaRepository.save(novaEmpresa);
                });
    }
}