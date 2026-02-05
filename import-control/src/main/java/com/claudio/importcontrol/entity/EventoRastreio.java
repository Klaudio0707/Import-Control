package com.claudio.importcontrol.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "eventos_rastreio")
public class EventoRastreio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataEvento;
    private String descricao;
    private String localizacao;
    @Column(nullable = true)
    private String rastreioDocumento;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_importacao_id")
    private ProcessoImportacao processo;
    
      public EventoRastreio() {}

public Long getId() { return id; }

    public LocalDateTime getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDateTime dataEvento) { this.dataEvento = dataEvento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getRastreioDocumento() { return rastreioDocumento; }
    public void setRastreioDocumento(String rastreioDocumento) { this.rastreioDocumento = rastreioDocumento; }

    public ProcessoImportacao getProcesso() { return processo; }
    public void setProcesso(ProcessoImportacao processo) { this.processo = processo; }

}