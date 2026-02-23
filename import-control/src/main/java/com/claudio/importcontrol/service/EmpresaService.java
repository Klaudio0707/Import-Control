package com.claudio.importcontrol.service;

import com.claudio.importcontrol.entity.Empresa;
import com.claudio.importcontrol.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private CnpjService cnpjService;

    @Transactional // Garante que se algo falhar, nada será salvo no banco
    public Empresa salvarEmpresaPeloCnpj(String cnpjDigitado) {
        var dados = cnpjService.consultarCnpj(cnpjDigitado);

        boolean isAtiva = "ATIVA".equalsIgnoreCase(dados.situacaoCadastral());

        if (!isAtiva) {
            throw new RuntimeException("Bloqueio de Cadastro: Empresa possui situação: " + dados.situacaoCadastral());
        }


        Empresa empresa = new Empresa();
        empresa.setCnpj(cnpjDigitado.replaceAll("\\D", ""));
        empresa.setRazaoSocial(dados.razaoSocial());
        empresa.setNomeFantasia(dados.nomeFantasia());
        empresa.setMunicipio(dados.municipio());
        empresa.setUf(dados.uf());
        empresa.setActive(true);

        // 4. Persistência
        return empresaRepository.save(empresa);
    }
}