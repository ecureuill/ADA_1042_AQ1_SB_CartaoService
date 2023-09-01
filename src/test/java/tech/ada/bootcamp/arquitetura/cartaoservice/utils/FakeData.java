package tech.ada.bootcamp.arquitetura.cartaoservice.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;

public class FakeData {

    public static Usuario gerarUsuario(){
        return gerarUsuario(0);
    }

    public static Usuario gerarUsuario(int countDependent){
        var usuario = new Usuario();
        usuario.setIdentificador(Faker.instance().regexify("[0-9]{11}"));
        usuario.setNome(Faker.instance().name().fullName());
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

    public static Dependente gerarDependente(){
        var dependente = new Dependente();
        dependente.setNome(Faker.instance().name().fullName());
        dependente.setCpf(Faker.instance().regexify("[0-9]{11}"));
        return dependente;
    }
}
