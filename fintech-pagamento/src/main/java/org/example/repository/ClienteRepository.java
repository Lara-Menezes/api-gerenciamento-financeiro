package org.example.repository;

import org.example.entity.Cliente;
import org.example.entity.StatusBloqueio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByStatusBloqueio(StatusBloqueio status);
    List<Cliente> findByNome(String nome);
    List<Cliente> findByCpf(String cpf);
}
