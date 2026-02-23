package com.claudio.importcontrol.service;


import com.claudio.importcontrol.dto.CnpjResDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CnpjService {

    // O RestTemplate é o cliente HTTP padrão do Spring
    private static final RestTemplate restTemplate = new RestTemplate();

    public static CnpjResDTO consultarCnpj(String cnpj) {
        // Limpa o CNPJ para garantir que só tenha números
        String cleanCnpj = cnpj.replaceAll("\\D", "");
        String url = "https://brasilapi.com.br/api/cnpj/v1/" + cleanCnpj;

        try {
            // O RestTemplate já converte o JSON da API para o seu DTO automaticamente
            return restTemplate.getForObject(url, CnpjResDTO.class);
        } catch (Exception e) {
            // Se o CNPJ não existir, a API retorna 404 e cai aqui
            throw new RuntimeException("Erro ao consultar CNPJ: " + e.getMessage());
        }
    }
}