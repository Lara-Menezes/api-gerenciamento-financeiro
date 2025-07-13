
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.example.entity.Fatura;
import org.example.entity.StatusFatura;
import org.example.repository.FaturaRepository;
import org.example.service.ClienteService;
import org.example.service.FaturaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FaturaServiceTest {

    @Mock
    private FaturaRepository faturaRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private FaturaService faturaService;

    @Test
    void deveMarcarFaturaComoPaga() {
        Fatura fatura = new Fatura();
        fatura.setId(1L);
        fatura.setStatus(StatusFatura.B);
        fatura.setValor(new BigDecimal("500.00"));
        fatura.setDataPagamento(null);

        Mockito.when(faturaRepository.findById(1L)).thenReturn(Optional.of(fatura));

        faturaService.registrarPagamento(1L);

        Assertions.assertEquals(StatusFatura.P, fatura.getStatus());
        Assertions.assertNotNull(fatura.getDataPagamento());
        Mockito.verify(faturaRepository).save(fatura);
    }
}

