package com.claudio.importcontrol.service;

import com.claudio.importcontrol.dto.CnpjResDTO;
import com.claudio.importcontrol.exception.CnpjNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CnpjService {

    private final RestTemplate restTemplate;

    // Injeção limpa no construtor. Sem uso de "static"
    public CnpjService() {
        this.restTemplate = new RestTemplate();
    }

    public CnpjResDTO consultarCnpj(String cnpj) {
        String cleanCnpj = cnpj.replaceAll("\\D", "");
        String url = "https://brasilapi.com.br/api/cnpj/v1/" + cleanCnpj;
        try {
            return restTemplate.getForObject(url, CnpjResDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CnpjNaoEncontradoException("O CNPJ " + cnpj + " é inválido ou não consta na base da Receita Federal.");
        } catch (Exception e) {
            throw new RuntimeException("Falha na integração com a BrasilAPI: " + e.getMessage());
        }
    }
}