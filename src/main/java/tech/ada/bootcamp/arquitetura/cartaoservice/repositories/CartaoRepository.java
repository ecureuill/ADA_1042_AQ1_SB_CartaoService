package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

  Cartao findFirstByUsuarioIdentificador(String id);

}