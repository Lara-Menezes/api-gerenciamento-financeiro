package org.example.dto;

import java.math.BigDecimal;

/**
 * Transfere dados entre o cliente (frontend) e o backend
 */
public class FaturaDTO {

    private BigDecimal valor;
    private String dataVencimento;
    private Long clienteId;
    private String status;

    /**
     * Getters e Setters
     */
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

