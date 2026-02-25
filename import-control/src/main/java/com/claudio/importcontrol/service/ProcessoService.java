package com.claudio.importcontrol.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.claudio.importcontrol.dto.ProcessoDTO;
import com.claudio.importcontrol.entity.CondicaoPagamento;
import com.claudio.importcontrol.entity.ProcessoImportacao;
import com.claudio.importcontrol.entity.Usuario;
import com.claudio.importcontrol.repository.CondicaoPagamentoRepository;
import com.claudio.importcontrol.repository.ProcessoRepository;
import com.claudio.importcontrol.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessoService {

    private final ProcessoRepository repository;
    private final EventoService eventoService;
    private final UsuarioRepository usuarioRepository;
    private final CondicaoPagamentoRepository condicaoPagamentoRepository;

    public ProcessoService(ProcessoRepository repository,
                           UsuarioRepository usuarioRepository,
                           EventoService eventoService,
                           CondicaoPagamentoRepository condicaoPagamentoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.eventoService = eventoService;
        this.condicaoPagamentoRepository = condicaoPagamentoRepository;
    }

    public List<ProcessoImportacao> listar() {
        List<ProcessoImportacao> processos = repository.findAll();
        if (processos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum processo encontrado.");
        }
        return processos;
    }

    @Transactional
    public ProcessoImportacao salvar(ProcessoDTO dados) {
        if (repository.existsByNumeroProcesso(dados.numeroProcesso())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um processo cadastrado com o número " + dados.numeroProcesso());
        }

        ProcessoImportacao processo = new ProcessoImportacao();

        // Regra da Condição de Pagamento
        if (dados.condicaoPagamentoId() != null) {
            CondicaoPagamento condicao = condicaoPagamentoRepository.findById(dados.condicaoPagamentoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Condição de Pagamento não encontrada. ID: " + dados.condicaoPagamentoId()));
            processo.setCondicaoPagamento(condicao);
        }

        processo.setNumeroProcesso(dados.numeroProcesso());
        processo.setIdentificadorInvoice(dados.identificadorInvoice());
        processo.setFornecedor(dados.fornecedor());
        processo.setProduto(dados.produto());
        processo.setQuantidade(dados.quantidade());
        processo.setPrecoPorQuilo(dados.precoPorQuilo());
        processo.setUnidadeMedida(dados.unidadeMedida());

        // Datas
        processo.setPrevisaoEmbarque(dados.previsaoEmbarque());
        processo.setDataEmbarque(dados.dataEmbarque());

        // Enums
        processo.setStatusLogistico(dados.statusProcesso());
        processo.setStatusFinanceiro(dados.statusPagamento());
        processo.setDI(dados.DI());

        if (dados.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dados.usuarioId()));
            processo.setUsuario(usuario);
        }

        ProcessoImportacao processoSalvo = repository.save(processo);
        eventoService.registrarAutomatico(
                processoSalvo.getId(),
                processoSalvo.getStatusLogistico().toString(),
                "Processo de Importação Criado com Sucesso"
        );

        return processoSalvo;
    }

    @Transactional
    public ProcessoImportacao atualizar(String id, ProcessoDTO dados) {
        ProcessoImportacao processo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não encontrado."));

        if (!processo.getNumeroProcesso().equals(dados.numeroProcesso())) {
            if (repository.existsByNumeroProcesso(dados.numeroProcesso())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um processo cadastrado com o número " + dados.numeroProcesso());
            }
            processo.setNumeroProcesso(dados.numeroProcesso());
        }

        if (dados.condicaoPagamentoId() != null) {
            CondicaoPagamento condicao = condicaoPagamentoRepository.findById(dados.condicaoPagamentoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Condição de Pagamento não encontrada. ID: " + dados.condicaoPagamentoId()));
            processo.setCondicaoPagamento(condicao);
        }

        processo.setIdentificadorInvoice(dados.identificadorInvoice());
        processo.setFornecedor(dados.fornecedor());
        processo.setProduto(dados.produto());
        processo.setQuantidade(dados.quantidade());
        processo.setUnidadeMedida(dados.unidadeMedida());
        processo.setPrecoPorQuilo(dados.precoPorQuilo());
        processo.setPrevisaoEmbarque(dados.previsaoEmbarque());
        processo.setDataEmbarque(dados.dataEmbarque());
        processo.setDataChegada(dados.dataChegada());
        processo.setDI(dados.DI());

        processo.setStatusLogistico(dados.statusProcesso());
        processo.setStatusFinanceiro(dados.statusPagamento());

        return repository.save(processo);
    }

    public List<ProcessoImportacao> buscarPorFornecedor(String nome) {
        return repository.findByFornecedorContainingIgnoreCase(nome);
    }

    public List<ProcessoImportacao> buscarMaioresQue(Double quantidade) {
        return repository.buscarAcimaDe(quantidade);
    }

    public ProcessoImportacao buscarPorId(String id) {
        return repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo com ID " + id + " não encontrado.")
        );
    }

    public void excluir(String id) {
        ProcessoImportacao processo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo com ID " + id + " não encontrado."));
        repository.delete(processo);
    }
}