package org.example.repository;

import org.example.entity.Fatura;
import org.example.enums.StatusFatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório JPA para operações com a entidade Fatura
 */
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    List<Fatura> findByStatus(StatusFatura status);
    List<Fatura> findByClienteId(Long clienteId);
}
