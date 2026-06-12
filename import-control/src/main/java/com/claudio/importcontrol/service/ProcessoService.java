package com.claudio.importcontrol.service;

import java.util.List;

import com.claudio.importcontrol.dto.ProcessoResponseDTO;
import com.claudio.importcontrol.enums.StatusPagamento;
import com.claudio.importcontrol.enums.StatusProcesso;
import com.claudio.importcontrol.enums.UnidadeMedida;
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
    private final CondicaoPagamentoService condicaoService;

    public ProcessoService(ProcessoRepository repository,
                           UsuarioRepository usuarioRepository,
                           EventoService eventoService,
                           CondicaoPagamentoRepository condicaoPagamentoRepository,
                           CondicaoPagamentoService condicaoService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.eventoService = eventoService;
        this.condicaoPagamentoRepository = condicaoPagamentoRepository;
        this.condicaoService = condicaoService;
    }

    // --- MÉTODOS DE LEITURA (Retornam DTOs) ---

    public List<ProcessoResponseDTO> listar() {
        // Usa o método otimizado para evitar N+1
        return repository.findAllComRelacionamentos().stream()
                .map(ProcessoResponseDTO::new)
                .toList();
    }

    public List<ProcessoResponseDTO> buscarPorFornecedor(String nome) {
        return repository.findByFornecedorContainingIgnoreCase(nome).stream()
                .map(ProcessoResponseDTO::new)
                .toList();
    }

    public List<ProcessoResponseDTO> buscarMaioresQue(Double quantidade) {
        return repository.buscarAcimaDe(quantidade).stream()
                .map(ProcessoResponseDTO::new)
                .toList();
    }

    public ProcessoResponseDTO buscarPorIdDTO(String id) {
        return new ProcessoResponseDTO(buscarEntidadePorId(id));
    }

    // --- MÉTODOS DE ESCRITA E AUXILIARES (Lidam com Entidades) ---

    // Método auxiliar privado para buscar a entidade sem expor o banco
    private ProcessoImportacao buscarEntidadePorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não encontrado."));
    }

    @Transactional
    public ProcessoImportacao salvar(ProcessoDTO dados) {
        ProcessoImportacao novoProcesso = new ProcessoImportacao();
        mapearDados(novoProcesso, dados);

        if (dados.condicaoPagamento() != null && dados.condicaoPagamento().descricao() != null) {
            Usuario usuario = novoProcesso.getUsuario();
            CondicaoPagamento condicao = condicaoService.buscarOuCriar(
                    dados.condicaoPagamento().descricao(),
                    dados.condicaoPagamento().diasPrazo(),
                    usuario
            );
            novoProcesso.setCondicaoPagamento(condicao);
        }

        return repository.save(novoProcesso);
    }

    @Transactional
    public ProcessoImportacao atualizar(String id, ProcessoDTO dados) {
        ProcessoImportacao processo = buscarEntidadePorId(id);

        if (!processo.getNumeroProcesso().equals(dados.numeroProcesso()) &&
                repository.existsByNumeroProcesso(dados.numeroProcesso())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um processo com o número " + dados.numeroProcesso());
        }

        mapearDados(processo, dados);
        return repository.save(processo);
    }

    public void excluir(String id) {
        // Agora busca a Entidade e deleta corretamente
        ProcessoImportacao processo = buscarEntidadePorId(id);
        repository.delete(processo);
    }

    private void mapearDados(ProcessoImportacao processo, ProcessoDTO dados) {
        processo.setNumeroProcesso(dados.numeroProcesso());
        processo.setIdentificadorInvoice(dados.identificadorInvoice());
        processo.setFornecedor(dados.fornecedor());
        processo.setProduto(dados.produto());
        processo.setQuantidade(dados.quantidade());
        processo.setPreco(dados.preco());
        processo.setTaxaCambio(dados.taxaCambio());
        processo.setUnidadeMedida(UnidadeMedida.valueOf(dados.unidadeMedida().toUpperCase()));
        processo.setPrevisaoEmbarque(dados.previsaoEmbarque());
        processo.setDataEmbarque(dados.dataEmbarque());
        processo.setDataChegada(dados.dataChegada());
        processo.setDI(dados.DI());
        processo.setStatusLogistico(StatusProcesso.valueOf(dados.statusProcesso().toUpperCase()));
        processo.setStatusFinanceiro(StatusPagamento.valueOf(dados.statusPagamento().toUpperCase()));

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
}