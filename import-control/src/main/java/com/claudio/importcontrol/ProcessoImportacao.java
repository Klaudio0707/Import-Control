package com.claudio.importcontrol;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProcessoImportacao {

    @Id
    private String id = UUID.randomUUID().toString();
    @Column(unique = true)
    private String numeroProcesso;
    private String identificadorInvoice;
    private String fornecedor;
    private String produto;
    private Double quantidade;
    private BigDecimal precoPorQuilo;
    private LocalDate dataEmbarque;

    public ProcessoImportacao() {
    }

    public ProcessoImportacao(String numeroProcesso, String invoice, String fornecedor) {
        this.numeroProcesso = numeroProcesso;
        this.identificadorInvoice = invoice;
        this.fornecedor = fornecedor;
    }

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

    void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    void setIdentificadorInvoice(String identificadorInvoice) {
        this.identificadorInvoice = identificadorInvoice;
    }

    void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}
