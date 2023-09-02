package tech.ada.bootcamp.arquitetura.cartaoservice.services.cartao;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;

@Service
@RequiredArgsConstructor
public class BuscarCartaoUseCase {

  private final CartaoRepository repository;

  public Cartao findByUserId(String userId) {
    return repository.findFirstByUsuarioIdentificador(userId);
  }
}
