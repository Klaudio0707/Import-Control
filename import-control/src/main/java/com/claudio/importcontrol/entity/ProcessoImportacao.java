package com.claudio.importcontrol.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


import jakarta.persistence.*;

@Entity
@Table(name = "processos_importacao")
public class ProcessoImportacao {

    @Id
    private String id = UUID.randomUUID().toString();
    @Column(unique = true)
    // private static final long idUsuario = idUsuario;
    private String numeroProcesso;
    private String identificadorInvoice;
    private String fornecedor;
    private String produto;
    private Double quantidade;
    private BigDecimal precoPorQuilo;
    private LocalDate dataEmbarque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public ProcessoImportacao() {
    }

    public ProcessoImportacao(String numeroProcesso, String invoice, String fornecedor) {
        this.numeroProcesso = numeroProcesso;
        this.identificadorInvoice = invoice;
        this.fornecedor = fornecedor;
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public String getId() {
        return id;
    }

    public String getIdentificadorInvoice() {
        return identificadorInvoice;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public String getProduto() {
        return produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoPorQuilo() {
        return precoPorQuilo;
    }

    public LocalDate getDataEmbarque() {
        return dataEmbarque;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoPorQuilo(BigDecimal precoPorQuilo) {
        this.precoPorQuilo = precoPorQuilo;
    }

    public void setDataEmbarque(LocalDate data) {
        this.dataEmbarque = data;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public void setIdentificadorInvoice(String identificadorInvoice) {
        this.identificadorInvoice = identificadorInvoice;
    }

    public void setFornecedor(String fornecedorInvoice) {
        this.fornecedor = fornecedorInvoice;
    }
    
}
