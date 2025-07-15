package org.example.controller;

import org.example.dto.FaturaDTO;
import org.example.entity.Cliente;
import org.example.entity.Fatura;
import org.example.enums.StatusFatura;
import org.example.repository.ClienteRepository;
import org.example.service.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/faturas")
@CrossOrigin(origins = "*") // Permite chamadas de qualquer origem
public class FaturaController {

    private final FaturaService faturaService;
    private final ClienteRepository clienteRepository;

    @Autowired
    public FaturaController(FaturaService faturaService, ClienteRepository clienteRepository) {
        this.faturaService = faturaService;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Endpoints HTTP da API para rotas da fatura
     */

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

    @PutMapping("/atualizar-atrasadas")
    public ResponseEntity<Void> atualizarAtrasadas() {
        faturaService.atualizarFaturasAtrasadas();
        return ResponseEntity.ok().build();
    }

    /**
     * Cria uma nova fatura a partir de um DTO
     */
    @PostMapping
    public ResponseEntity<?> criarFatura(@RequestBody FaturaDTO dto) {
        if (dto.getClienteId() == null) {
            return ResponseEntity.badRequest().body("ID do cliente é obrigatório.");
        }

        // Busca cliente no banco
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        // Criação da fatura a partir da DTO
        Fatura fatura = new Fatura();
        fatura.setCliente(cliente);
        fatura.setValor(dto.getValor());
        fatura.setDataVencimento(LocalDate.parse(dto.getDataVencimento()));
        fatura.setStatus(StatusFatura.valueOf(dto.getStatus()));

        return ResponseEntity.ok(faturaService.criarFatura(fatura));
    }
}
