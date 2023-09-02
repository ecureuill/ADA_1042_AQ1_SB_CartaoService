package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Fatura;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    
    @Query("SELECT f FROM Fatura f WHERE f.cartao.numeroCartao = :numeroCartao ORDER BY f.createdAt DESC")
    Optional<Fatura> getLastFaturaFromCartao(String numeroCartao);
    List<Fatura> findByCartaoNumeroCartao(String numeroCartao);
    List<Fatura> findByCartaoIn(List<Cartao> cartoes);
    List<Fatura> findByCartao(Cartao cartao);
    
    @Query("SELECT f FROM Fatura f WHERE f.cartao = :cartao AND MONTH(f.dataVencimento) = :month AND YEAR(f.dataVencimento) = :year")
    Optional<Fatura> findByCartaoAndMonthYear(@Param("cartao") Cartao cartao, @Param("month") int month, @Param("year") int year);



}
