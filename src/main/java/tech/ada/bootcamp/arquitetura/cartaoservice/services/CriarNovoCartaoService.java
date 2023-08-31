package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
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
    private static Random random;
    
    public Cartao execute(TipoCartao tipoCartao, Usuario usuario){
        LocalDate dataAtual = LocalDate.now();
        Cartao cartao = new Cartao();

        cartao.setTipoCartao(tipoCartao);
        cartao.setUsuario(usuario);
        cartao.setIdContaBanco(UUID.randomUUID().toString());
        cartao.setNomeTitular(usuario.getNome());
        cartao.setVencimentoCartao(dataAtual.plusYears(5));
        cartao.setCodigoSeguranca(gerarNumeroAleatorio(3));
        cartao.setNumeroCartao(gerarNumeroAleatorio(12));
        cartao.setCreatedAt(LocalDateTime.now());

        return cartaoRepository.save(cartao);
    }

    private String gerarNumeroAleatorio(int length) {

        final int tamanho = length<=0?1:length;
        IntStream stream =  getRandom().ints(tamanho,0,9);
        StringBuilder builder = new StringBuilder();
        stream.forEachOrdered(builder::append);
        return builder.toString();
    }

    private static Random getRandom(){
        if(Objects.isNull(random)){
            random = new Random();
        }
        return random;
    }

    public Cartao cadastrarAdicional(String nome, TipoCartao tipoCartao, Usuario usuario) {
        Cartao cartao = new Cartao();
        cartao.setDependente(true);
        cartao.setTipoCartao(tipoCartao);
        cartao.setUsuario(usuario);
        cartao.setIdContaBanco(UUID.randomUUID().toString());
        cartao.setNomeTitular(nome);
        cartao.setVencimentoCartao(LocalDate.now().plusYears(5));
        cartao.setCodigoSeguranca(gerarNumeroAleatorio(3));
        cartao.setNumeroCartao(gerarNumeroAleatorio(12));
        cartao.setCreatedAt(LocalDateTime.now());

        return cartaoRepository.save(cartao);
    }
}
