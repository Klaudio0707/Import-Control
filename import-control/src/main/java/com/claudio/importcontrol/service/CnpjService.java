package com.claudio.importcontrol.service;


import com.claudio.importcontrol.dto.CnpjResDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CnpjService {
    private static final RestTemplate restTemplate = new RestTemplate();

    public static CnpjResDTO consultarCnpj(String cnpj) {
        String cleanCnpj = cnpj.replaceAll("\\D", "");
        String url = "https://brasilapi.com.br/api/cnpj/v1/" + cleanCnpj;

        try {
            return restTemplate.getForObject(url, CnpjResDTO.class);
        } catch (Exception e) {
            //  a API retorna 404 e cai aqui
            throw new RuntimeException("Erro ao consultar CNPJ: " + e.getMessage());
        }
    }
}