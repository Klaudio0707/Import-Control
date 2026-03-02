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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um processo com o número: " + dados.numeroProcesso());
        }

        ProcessoImportacao processo = new ProcessoImportacao();

        // Mapeia todos os campos e relacionamentos de uma vez só
        mapearDados(processo, dados);

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

        if (!processo.getNumeroProcesso().equals(dados.numeroProcesso()) &&
                repository.existsByNumeroProcesso(dados.numeroProcesso())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um processo com o número " + dados.numeroProcesso());
        }

        mapearDados(processo, dados);

        return repository.save(processo);
    }


    private void mapearDados(ProcessoImportacao processo, ProcessoDTO dados) {
        processo.setNumeroProcesso(dados.numeroProcesso());
        processo.setIdentificadorInvoice(dados.identificadorInvoice());
        processo.setFornecedor(dados.fornecedor());
        processo.setProduto(dados.produto());
        processo.setQuantidade(dados.quantidade());
        processo.setPreco(dados.preco());
        processo.setTaxaCambio(dados.taxaCambio());
        processo.setUnidadeMedida(dados.unidadeMedida());
        processo.setPrevisaoEmbarque(dados.previsaoEmbarque());
        processo.setDataEmbarque(dados.dataEmbarque());
        processo.setDataChegada(dados.dataChegada());
        processo.setDI(dados.DI());
        processo.setStatusLogistico(dados.statusProcesso());
        processo.setStatusFinanceiro(dados.statusPagamento());

        if (dados.condicaoPagamentoId() != null) {
            CondicaoPagamento condicao = condicaoPagamentoRepository.findById(dados.condicaoPagamentoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Condição de Pagamento não encontrada."));
            processo.setCondicaoPagamento(condicao);
        }


        if (dados.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado."));
            processo.setUsuario(usuario);
        }
    }


    public List<ProcessoImportacao> buscarPorFornecedor(String nome) {
        return repository.findByFornecedorContainingIgnoreCase(nome);
    }

    public List<ProcessoImportacao> buscarMaioresQue(Double quantidade) {
        return repository.buscarAcimaDe(quantidade);
    }

    public ProcessoImportacao buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não encontrado.")
        );
    }

    public void excluir(String id) {
        ProcessoImportacao processo = buscarPorId(id);
        repository.delete(processo);
    }
}