package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CompraRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CompraRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.utils.FakeData;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;
    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CompraService compraService;

    @Test
    @DisplayName("Should register purchase successfully")
    public void shouldRegisterPurchaseSucccessfully() {
        Compra compra = FakeData.gerarCompra();
        CompraRequest compraRequest = createCompraRequest(compra);

        when(cartaoRepository.findById(Mockito.any())).thenReturn(Optional.of(compra.cartao));
        when(compraRepository.save(Mockito.any(Compra.class))).thenReturn(compra);

        compraService.cadastrar(compraRequest);

        ArgumentCaptor<Compra> compraArgumentCaptor = ArgumentCaptor.forClass(Compra.class);

        Mockito.verify(compraRepository, Mockito.times(1)).save(compraArgumentCaptor.capture());


        assertions(compra, compraArgumentCaptor.getValue());
        Mockito.verify(cartaoRepository, Mockito.times(1)).findById(compraRequest.getNumeroCartao());




    }
    public void assertions(Compra expected, Compra current){
        Assertions.assertEquals(expected.getLoja(), current.getLoja());
        Assertions.assertEquals(expected.getDataCompra(), current.getDataCompra());
        Assertions.assertEquals(expected.getValor(), current.getValor());
        Assertions.assertEquals(expected.getCartao(), current.getCartao());
    }

    public CompraRequest createCompraRequest(Compra compra){
        CompraRequest compraRequest = new CompraRequest();
        compraRequest.setNumeroCartao(compra.getCartao().getNumeroCartao());
        compraRequest.setLoja(compra.getLoja());
        compraRequest.setDataCompra(compra.getDataCompra());
        compraRequest.setValor(compra.getValor());

        return compraRequest;
    }
}
