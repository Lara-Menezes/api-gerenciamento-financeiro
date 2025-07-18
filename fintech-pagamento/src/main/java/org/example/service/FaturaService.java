package org.example.service;

import org.example.entity.Cliente;
import org.example.entity.Fatura;
import org.example.enums.StatusBloqueio;
import org.example.enums.StatusFatura;
import org.example.repository.ClienteRepository;
import org.example.repository.FaturaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Lógica de negócio relacionada às Faturas
 */
@Service
public class FaturaService {

    private final FaturaRepository faturaRepository;
    private final ClienteRepository clienteRepository;

    public FaturaService(FaturaRepository faturaRepository, ClienteRepository clienteRepository) {
        this.faturaRepository = faturaRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Cria uma fatura vinculada a um cliente existente
     * Define padrão status como ABERTA e limpa data de pagamento
     */
    public Fatura criarFatura(Fatura fatura) {
        Cliente cliente = clienteRepository.findById(fatura.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        fatura.setCliente(cliente);
        fatura.setStatus(StatusFatura.B); // B = Aberta
        fatura.setDataPagamento(null);

        return faturaRepository.save(fatura);
    }

    public Fatura registrarPagamento(Long faturaId) {
        Fatura fatura = faturaRepository.findById(faturaId)
                .orElseThrow(() -> new RuntimeException("Fatura não encontrada"));

        fatura.setDataPagamento(LocalDate.now());
        fatura.setStatus(StatusFatura.P); // P = Pago

        if (fatura.getDataPagamento().isAfter(fatura.getDataVencimento().plusDays(3))) {
            Cliente cliente = fatura.getCliente();
            cliente.setStatusBloqueio(StatusBloqueio.B);
            cliente.setLimiteCredito(BigDecimal.ZERO);
            clienteRepository.save(cliente);
        }

        faturaRepository.save(fatura);
        return fatura;
    }

    /**
     * Atualiza o status das faturas abertas para atrasadas,
     * se já passaram 3 dias do vencimento e não foram pagas.
     * Bloqueia clientes com faturas atrasadas.
     */
    public void atualizarFaturasAtrasadas() {
        List<Fatura> faturas = faturaRepository.findByStatus(StatusFatura.B); // abertas

        for (Fatura f : faturas) {
            if (f.getDataVencimento().plusDays(3).isBefore(LocalDate.now()) && f.getDataPagamento() == null) {
                f.setStatus(StatusFatura.A); // atrasada
                faturaRepository.save(f);

                Cliente cliente = f.getCliente();
                cliente.setStatusBloqueio(StatusBloqueio.B);
                cliente.setLimiteCredito(BigDecimal.ZERO);
                clienteRepository.save(cliente);
            }
        }
    }

    public List<Fatura> listarFaturasPorCliente(Long clienteId) {
        return faturaRepository.findByClienteId(clienteId);
    }

    public List<Fatura> listarFaturasAtrasadas() {
        return faturaRepository.findByStatus(StatusFatura.A);
    }
}
