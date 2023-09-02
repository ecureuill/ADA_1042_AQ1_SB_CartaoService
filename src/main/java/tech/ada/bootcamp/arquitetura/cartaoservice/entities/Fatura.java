package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fatura")
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "\"dataVencimento\"")
    private LocalDate dataVencimento;
    @Column(name = "\"dataProcessamento\"")
    private LocalDate dataProcessamento;

    private BigDecimal valor;
    @Column(name = "\"valorPago\"")
    private BigDecimal valorPago;

    @ManyToOne
    @JoinColumn(name = "\"numeroCartao\"")
    private Cartao cartao;

    @CreationTimestamp
    @Column(name = "\"createdAt\"")
    private LocalDate createdAt;
}