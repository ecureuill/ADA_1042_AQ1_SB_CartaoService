package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CompraRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.CompraResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CompraRepository;

import java.util.Optional;
import java.util.UUID;

@Service

public class CompraService {
    private CompraRepository compraRepository;
    private CartaoRepository cartaoRepository;

    public CompraService(CompraRepository compraRepository, CartaoRepository cartaoRepository) {
        this.compraRepository = compraRepository;
        this.cartaoRepository = cartaoRepository;
    }

    public Compra cadastrar(CompraRequest compraRequest){
        Compra compra = new Compra();
        UUID uuid = UUID.randomUUID();
        compra.setId(uuid);
        compra.setLoja(compraRequest.getLoja());
        compra.setDataCompra(compraRequest.getDataCompra());
        compra.setValor(compraRequest.getValor());

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(compraRequest.getNumeroCartao());

        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            compra.setCartao(cartao);
        } else {
            String errorMessage = "Cartão não encontrado. Favor checar o número de cartão fornecido";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return compraRepository.save(compra);


    }
}
