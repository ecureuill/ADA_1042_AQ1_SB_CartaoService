package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.TipoCartao;

@Data
@Entity
@Table(name = "\"cartao\"")
public class Cartao {
    @Id 
    @Column(name = "\"numeroCartao\"")
    private String numeroCartao;
    @Column(name = "\"nomeTitular\"")
    private String nomeTitular;
    @Column(name = "\"vencimentoCartao\"")
    private LocalDate vencimentoCartao;
    @Column(name = "\"codigoSeguranca\"")
    private String codigoSeguranca;
    @Column(name = "\"tipoCartao\"")
    private TipoCartao tipoCartao;
    @Column(name = "\"idContaBanco\"")
    private String idContaBanco;
    @Column(name = "\"dependente\"")
    private Boolean dependente = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "\"usuarioIdentificador\"")
    private Usuario usuario;

    @CreatedDate
    @Column(name = "\"createdAt\"")
    private LocalDateTime createdAt;

}