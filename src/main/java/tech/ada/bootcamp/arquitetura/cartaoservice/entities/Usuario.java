package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    private String identificador;
    private String nome;

    @Embedded
    Endereco endereco;

    @CreatedDate
    @Column(name = "\"createdAt\"")
    private LocalDateTime createdAt;

}
