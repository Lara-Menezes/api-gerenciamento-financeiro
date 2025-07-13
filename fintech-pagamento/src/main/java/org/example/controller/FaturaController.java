package org.example.controller;

import org.example.entity.Fatura;
import org.example.service.FaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faturas")
@CrossOrigin(origins = "*")
public class FaturaController {

    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Fatura>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(faturaService.listarFaturasPorCliente(clienteId));
    }

    @PutMapping("/{id}/pagamento")
    public ResponseEntity<Fatura> registrarPagamento(@PathVariable Long id) {
        return ResponseEntity.ok(faturaService.registrarPagamento(id));
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<Fatura>> listarAtrasadas() {
        return ResponseEntity.ok(faturaService.listarFaturasAtrasadas());
    }

    // opcional: rodar atualização de faturas atrasadas manualmente
    @PutMapping("/atualizar-atrasadas")
    public ResponseEntity<Void> atualizarAtrasadas() {
        faturaService.atualizarFaturasAtrasadas();
        return ResponseEntity.ok().build();
    }
}
