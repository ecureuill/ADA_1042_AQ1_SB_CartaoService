package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, UUID> {

    Boolean existsByCpf(String cpf);
    
}
