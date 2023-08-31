package tech.ada.bootcamp.arquitetura.cartaoservice.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.github.javafaker.Faker;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;

public class FakeData {

    public static Usuario gerarUsuario(){
        var usuario = new Usuario();
        usuario.setIdentificador(Faker.instance().regexify("[0-9]{11}"));
        usuario.setNome(Faker.instance().name().fullName());
        usuario.setEndereco(gerarEndereco());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setDepenadentes(new ArrayList<Dependente>());
        return usuario;
    }

    public static Endereco gerarEndereco(){
        var endereco = new Endereco();
        endereco.setRua(Faker.instance().address().streetAddress());
        endereco.setBairro(Faker.instance().address().country());
        endereco.setComplemento(Faker.instance().address().secondaryAddress());
        endereco.setNumero(Faker.instance().address().streetAddressNumber())
        ;
        endereco.setCidade(Faker.instance().address().city());
        endereco.setEstado(Faker.instance().address().state());
        endereco.setCep(Faker.instance().address().zipCode());

        return endereco;
    }
}
