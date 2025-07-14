package org.example.service;

import org.example.entity.Cliente;
import org.example.entity.StatusBloqueio;
import org.example.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("CPF não encontrado"));
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente atualizado) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(atualizado.getNome());
        cliente.setCpf(atualizado.getCpf());
        cliente.setDataNascimento(atualizado.getDataNascimento());
        cliente.setStatusBloqueio(atualizado.getStatusBloqueio());
        cliente.setLimiteCredito(atualizado.getLimiteCredito());
        return clienteRepository.save(cliente);
    }

    public void bloquearCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setStatusBloqueio(StatusBloqueio.B);
        cliente.setLimiteCredito(java.math.BigDecimal.ZERO);
        clienteRepository.save(cliente);
    }

    public List<Cliente> listarBloqueados() {
        return clienteRepository.findByStatusBloqueio(StatusBloqueio.B);
    }
}
