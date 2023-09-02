package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

    List<Cartao> findByUsuarioIdentificador(String usuarioIdentificador);
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}