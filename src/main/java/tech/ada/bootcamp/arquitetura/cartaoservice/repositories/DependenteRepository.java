package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, UUID> {

  @Query("SELECT d FROM Dependente d WHERE d.usuario.identificador = :id")
  List<Dependente> findByUsuarioIdentificador(String id);

}
