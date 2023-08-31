package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.DependenteRequest;

@Entity
@Table(name = "dependente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dependente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cpf;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "\"usuarioIdentificador\"")
    private Usuario usuario;

    @OneToOne()
    @JoinColumn(name = "\"numeroCartao\"")
    private Cartao cartao;

    @CreatedDate
    @Column(name = "\"createdAt\"")
    private LocalDateTime createdAt;

    public Dependente(DependenteRequest dto) {
        this.setCpf(dto.cpf());
        this.setNome(dto.nome());
    }
}
