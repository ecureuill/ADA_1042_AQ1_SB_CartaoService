package tech.ada.bootcamp.arquitetura.cartaoservice.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.TipoCartao;

public class FakeData {
    private static final Faker FAKER = new Faker(Locale.US);
    private static final int RANDOM_INDEX = (int) Math.random() * TipoCartao.values().length;
    private static final TipoCartao RANDOM_TIPO_CARTAO = TipoCartao.values()[RANDOM_INDEX];

    public static Usuario gerarUsuario(){
        return gerarUsuario(0);
    }

    public static Usuario gerarUsuario(int countDependent){
        var usuario = new Usuario();
        usuario.setIdentificador(FAKER.regexify("[0-9]{11}"));
        usuario.setNome(FAKER.name().fullName());
        usuario.setEndereco(gerarEndereco());
        usuario.setCreatedAt(LocalDateTime.now());

        List<Dependente> dependentes = new ArrayList<Dependente>();
        for(int i = 0; i < countDependent; i++){
            dependentes.add(gerarDependente());
        }
        usuario.setDependentes(dependentes);
        return usuario;
    }

    public static Endereco gerarEndereco(){
        var endereco = new Endereco();
        endereco.setRua(FAKER.address().streetAddress());
        endereco.setBairro(FAKER.address().country());
        endereco.setComplemento(FAKER.address().secondaryAddress());
        endereco.setNumero(FAKER.address().streetAddressNumber())
        ;
        endereco.setCidade(FAKER.address().city());
        endereco.setEstado(FAKER.address().state());
        endereco.setCep(FAKER.address().zipCode());

        return endereco;
    }

    public static Dependente gerarDependente(){
        var dependente = new Dependente();
        dependente.setNome(FAKER.name().fullName());
        dependente.setCpf(FAKER.regexify("[0-9]{11}"));
        return dependente;
    }

    public static Cartao gerarCartao(){
        
        var cartao = new Cartao();
        cartao.setNumeroCartao(FAKER.business().creditCardNumber());
        cartao.setCreatedAt(FAKER.date().past(360*7, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        cartao.setCodigoSeguranca(FAKER.number().digits(3));
        cartao.setDependente(FAKER.random().nextBoolean());
        cartao.setIdContaBanco(UUID.randomUUID().toString());
        cartao.setUsuario(gerarUsuario(cartao.getDependente()? 1 : 0));
        cartao.setNomeTitular(cartao.getDependente()? cartao.getUsuario().getDependentes().get(0).getNome() : cartao.getUsuario().getNome());
        cartao.setTipoCartao(RANDOM_TIPO_CARTAO);
        cartao.setValidadeCartao(cartao.getCreatedAt().plusYears(5).toLocalDate());
        // implement cartao.setVencimentoCartao(); to address missing property during planning on cartao

        return cartao;
    }

    public static Compra gerarCompra(){
        var compra = new Compra();
        compra.setCartao(gerarCartao());
        compra.setDataCompra(LocalDate.now());
        compra.setLoja(FAKER.company().name());
        //TO-DO não está respeitando o locale
        compra.setValor(new BigDecimal(FAKER.commerce().price().replace(",", ".")));
        return compra;
    }
}
