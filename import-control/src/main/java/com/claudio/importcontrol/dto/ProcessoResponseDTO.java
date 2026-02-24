package com.claudio.importcontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record CnpjResDTO(
        @JsonProperty("razao_social") String razaoSocial,
        @JsonProperty("nome_fantasia") String nomeFantasia,
        @JsonProperty("descricao_situacao_cadastral") String situacaoCadastral,
        String municipio,
        String uf,
        String cep

){   }
