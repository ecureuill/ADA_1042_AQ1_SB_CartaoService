package tech.ada.bootcamp.arquitetura.cartaoservice.services.usuarios;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.UsuarioNotFoundException;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioUseCase {

  private final UsuarioRepository usuarioRepository;

  public Usuario FindById(String id) {
    var user = usuarioRepository.findById(id);

    if (user.isEmpty()) {
      throw new UsuarioNotFoundException("Usuário do id" + id + " não foi encontardo.");
    }

    return user.get();
  }
}
