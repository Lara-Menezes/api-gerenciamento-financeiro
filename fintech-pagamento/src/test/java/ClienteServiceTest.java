import java.math.BigDecimal;
import java.util.Optional;

import org.example.entity.Cliente;
import org.example.enums.StatusBloqueio;
import org.example.repository.ClienteRepository;
import org.example.service.ClienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveBloquearClienteEAtualizarLimiteParaZero() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setStatusBloqueio(StatusBloqueio.A);
        cliente.setLimiteCredito(new BigDecimal("1000.00"));

        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.bloquearCliente(1L);

        Assertions.assertEquals(StatusBloqueio.B, cliente.getStatusBloqueio());
        Assertions.assertEquals(BigDecimal.ZERO, cliente.getLimiteCredito());
        Mockito.verify(clienteRepository).save(cliente);
    }
}