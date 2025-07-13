package org.example.repository;

import org.example.entity.Fatura;
import org.example.entity.StatusFatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    List<Fatura> findByStatus(StatusFatura status);
    List<Fatura> findByClienteId(Long clienteId);
}
