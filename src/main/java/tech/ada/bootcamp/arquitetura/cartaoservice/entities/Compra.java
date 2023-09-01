package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Entity
@Table(name = "compra")
@JsonIgnoreProperties(value= {"dependentes"})
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "\"dataCompra\"")
    private LocalDate dataCompra;

    public BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "\"numeroCartao\"")
    public Cartao cartao;

    @CreationTimestamp
    @Column(name = "\"createdAt\"")
    private Instant createdAt;

    private String loja;

}