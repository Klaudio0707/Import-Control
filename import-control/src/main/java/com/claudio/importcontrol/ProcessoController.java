package com.claudio.importcontrol;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/processos") // O endereço do site será /processos
public class ProcessoController {

    @GetMapping
    public List<ProcessoImportacao> listar() {
        List<ProcessoImportacao> lista = new ArrayList<>();

        ProcessoImportacao p1 = new ProcessoImportacao("PO-2026", "INV-001", "Fornecedor China");
        p1.setProduto("Mouse Gamer RGB");
        p1.setQuantidade(500.0);
        p1.setPrecoPorQuilo(new BigDecimal("12.50"));
        p1.setDataEmbarque(LocalDate.now());

        lista.add(p1);

        return lista; // O Spring converte p JSON sozinho!
    }
}