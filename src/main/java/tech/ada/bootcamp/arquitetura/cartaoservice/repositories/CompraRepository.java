package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Query("SELECT SUM(c.valor) FROM Compra c WHERE c.cartao.numeroCartao = :numeroCartao")
    Optional<BigDecimal> sumValorByCartaoNumeroCartao(@Param("numeroCartao") String numeroCartao);

    List<Compra> findByCartao(Cartao cartao);
}