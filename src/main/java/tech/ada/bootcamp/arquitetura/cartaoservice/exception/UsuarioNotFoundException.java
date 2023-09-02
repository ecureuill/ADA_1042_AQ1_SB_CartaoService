package tech.ada.bootcamp.arquitetura.cartaoservice.exception;

public class UsuarioNotFoundException extends RuntimeException {
  public UsuarioNotFoundException() {
    super("Usuário não encontrado no sistema!");
  }

  public UsuarioNotFoundException(String message) {
    super(message);
  }
}
