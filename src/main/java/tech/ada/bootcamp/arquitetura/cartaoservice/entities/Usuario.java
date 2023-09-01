package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario"  )
    @JsonIgnore
    private List<Dependente> dependentes;

}
