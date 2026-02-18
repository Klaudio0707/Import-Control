package com.claudio.importcontrol.entity;

import com.claudio.importcontrol.enums.FormaPagamento;
import com.claudio.importcontrol.enums.StatusPagamento;
import com.claudio.importcontrol.enums.StatusProcesso;
import com.claudio.importcontrol.enums.UnidadeMedida;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "processos_importacao")
public class ProcessoImportacao {

    @Id
    private String id = UUID.randomUUID().toString();

    private String numeroProcesso;
    private String identificadorInvoice;
    private String fornecedor;
    private String produto;
    private Double quantidade;
    private BigDecimal precoPorQuilo;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida", length = 10)
    private UnidadeMedida unidadeMedida;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private LocalDate dataEmbarque;
    private LocalDate dataChegada;
    private LocalDate previsaoEmbarque;
    private String DI; 

    @Column(name = "dias_para_pagamento")
    private Integer diasParaPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_logistico")
    private StatusProcesso statusLogistico;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_financeiro")
    private StatusPagamento statusFinanceiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public ProcessoImportacao() {}

    public ProcessoImportacao(String numeroProcesso, String invoice, String fornecedor) {
        this.numeroProcesso = numeroProcesso;
        this.identificadorInvoice = invoice;
        this.fornecedor = fornecedor;
    }

   @PrePersist
    @PreUpdate
    public void prepararDados() {
        
        if (this.statusLogistico == null) this.statusLogistico = StatusProcesso.CRIADO;
        if (this.statusFinanceiro == null) this.statusFinanceiro = StatusPagamento.PENDENTE;

        if (this.dataEmbarque != null && this.diasParaPagamento != null) {
            this.dataVencimento = this.dataEmbarque.plusDays(this.diasParaPagamento);
        }


        if (this.quantidade != null && this.precoPorQuilo != null) {
         
            this.valorTotal = this.precoPorQuilo.multiply(BigDecimal.valueOf(this.quantidade));
        }
    }


    @Transient
    public String getStatusPrazo() {
        if (this.dataVencimento == null) return "Aguardando Embarque/Prazo";

        long dias = ChronoUnit.DAYS.between(LocalDate.now(), this.dataVencimento);

        if (dias > 0) {
            return "Vence em " + dias + " dias";
        } else if (dias < 0) {
            return "ATRASADO há " + Math.abs(dias) + " dias";
        } else {
            return "Vence HOJE!";
        }
    }
    


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNumeroProcesso() { return numeroProcesso; }
    public void setNumeroProcesso(String numeroProcesso) { this.numeroProcesso = numeroProcesso; }

    public String getIdentificadorInvoice() { return identificadorInvoice; }
    public void setIdentificadorInvoice(String identificadorInvoice) { this.identificadorInvoice = identificadorInvoice; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }

    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoPorQuilo() { return precoPorQuilo; }
    public void setPrecoPorQuilo(BigDecimal precoPorQuilo) { this.precoPorQuilo = precoPorQuilo; }

    public LocalDate getDataEmbarque() { return dataEmbarque; }
    public void setDataEmbarque(LocalDate dataEmbarque) { this.dataEmbarque = dataEmbarque; }

    public LocalDate getDataChegada() { return dataChegada; }
    public void setDataChegada(LocalDate dataChegada) { this.dataChegada = dataChegada; }

    public LocalDate getPrevisaoEmbarque() { return previsaoEmbarque; }
    public void setPrevisaoEmbarque(LocalDate previsaoEmbarque) { this.previsaoEmbarque = previsaoEmbarque; }

    public String getDI() { return DI; }
    public void setDI(String DI) { this.DI = DI; }

    public Integer getDiasParaPagamento() { return diasParaPagamento; }
    public void setDiasParaPagamento(Integer diasParaPagamento) { this.diasParaPagamento = diasParaPagamento; }

    public LocalDate getDataVencimento() { return dataVencimento; }
  

    public StatusProcesso getStatusLogistico() { return statusLogistico; }
    public void setStatusLogistico(StatusProcesso statusLogistico) { this.statusLogistico = statusLogistico; }

    public StatusPagamento getStatusFinanceiro() { return statusFinanceiro; }
    public void setStatusFinanceiro(StatusPagamento statusFinanceiro) { this.statusFinanceiro = statusFinanceiro; }

    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; }
    public BigDecimal getValorTotal() { return valorTotal; }
 

}