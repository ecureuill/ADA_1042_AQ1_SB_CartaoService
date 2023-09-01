package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CompraRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CompraRepository;

@Service
@RequiredArgsConstructor
public class CompraService {
    private final CompraRepository compraRepository;
    private final CartaoRepository cartaoRepository;

    public Compra cadastrar(CompraRequest compraRequest){
        Compra compra = criarCompra(compraRequest);
        Cartao cartao = buscarCartao(compraRequest);
        compra.setCartao(cartao);

        return compraRepository.save(compra);
    }

    private Cartao buscarCartao(CompraRequest compraRequest) {
        return cartaoRepository.findById(compraRequest.getNumeroCartao())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado. Favor checar o número de cartão fornecido"));
    }

    private Compra criarCompra(CompraRequest compraRequest) {
        Compra compra = new Compra();
        compra.setId(UUID.randomUUID());
        compra.setLoja(compraRequest.getLoja());
        compra.setDataCompra(compraRequest.getDataCompra());
        compra.setValor(compraRequest.getValor());

        return compra;
    }
}
