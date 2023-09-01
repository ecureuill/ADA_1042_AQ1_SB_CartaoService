package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.TipoCartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;

@Service
@RequiredArgsConstructor
public class CriarNovoCartaoService {
    private final CartaoRepository cartaoRepository;
    
    public Cartao execute(TipoCartao tipoCartao, Usuario usuario) {
        Cartao cartao = createCartao(tipoCartao, usuario, false);
        return cartaoRepository.save(cartao);
    }

    public Cartao cadastrarAdicional(String nome, TipoCartao tipoCartao, Usuario usuario) {
        Cartao cartao = createCartao(tipoCartao, usuario, true);
        cartao.setNomeTitular(nome);
        return cartaoRepository.save(cartao);
    }

    private Cartao createCartao(TipoCartao tipoCartao, Usuario usuario, boolean isDependente) {
        Cartao cartao = new Cartao();
        cartao.setDependente(isDependente);
        cartao.setTipoCartao(tipoCartao);
        cartao.setUsuario(usuario);
        cartao.setIdContaBanco(UUID.randomUUID().toString());
        cartao.setNomeTitular(usuario.getNome());
        cartao.setVencimentoCartao(LocalDate.now().plusYears(5));
        cartao.setCodigoSeguranca(gerarNumeroAleatorio(3));
        cartao.setNumeroCartao(gerarNumeroAleatorio(12));
        cartao.setCreatedAt(LocalDateTime.now());
        return cartao;
    }

    private String gerarNumeroAleatorio(int length) {
        IntStream stream = new Random().ints(length, 0, 9);
        StringBuilder builder = new StringBuilder();
        stream.forEachOrdered(builder::append);
        return builder.toString();
    }
}
