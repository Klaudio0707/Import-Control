package com.claudio.importcontrol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "condicoes_pagamento")
public class CondicaoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição é obrigatória (Ex: 'Net 30', 'À Vista', '30/60/90')")
    private String descricao;

    @NotNull(message = "O número de dias para o primeiro vencimento é obrigatório")
    @Column(name = "dias_prazo")
    private Integer diasPrazo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public CondicaoPagamento() {}

    public CondicaoPagamento(String descricao, Integer diasPrazo, Usuario usuario) {
        this.descricao = descricao;
        this.diasPrazo = diasPrazo;
        this.usuario = usuario;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getDiasPrazo() { return diasPrazo; }
    public void setDiasPrazo(Integer diasPrazo) { this.diasPrazo = diasPrazo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}