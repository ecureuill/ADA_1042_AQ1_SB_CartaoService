package tech.ada.bootcamp.arquitetura.cartaoservice.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Fatura;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.CompraResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.FaturaResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CompraRepository;

@Component
public class FaturaConverter {

    private CompraRepository compraRepository;

    public FaturaResponse convertToFaturaResponse(Fatura fatura) {
        FaturaResponse response = new FaturaResponse();
        
        response.setValor(fatura.getValor().doubleValue());
        response.setDataFaturaGerada(fatura.getCreatedAt());
        response.setDataVencimento(fatura.getDataVencimento());
        response.setDataProcessamento(fatura.getDataProcessamento());
        response.setValorPago(fatura.getValorPago());
        response.setNumeroCartao(fatura.getCartao().getNumeroCartao());
        
        List<Compra> compras = compraRepository.findByCartao(fatura.getCartao());
        List<CompraResponse> compraResponses = compras.stream()
            .map(this::convertToCompraResponse)
            .collect(Collectors.toList());
        
        response.setResumoCompra(compraResponses);
        
        return response;
    }

    public CompraResponse convertToCompraResponse(Compra compra) {
        CompraResponse response = new CompraResponse();

        response.setId(compra.getId());
        response.setNumeroCartao(compra.getCartao());
        response.setLoja(compra.getLoja());
        response.setValor(compra.getValor());
        response.setDataCompra(compra.getDataCompra());

        return response;
    }
}
