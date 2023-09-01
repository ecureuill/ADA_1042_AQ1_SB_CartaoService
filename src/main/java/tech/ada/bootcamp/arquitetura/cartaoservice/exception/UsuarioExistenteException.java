package tech.ada.bootcamp.arquitetura.cartaoservice.exception;

public class UsuarioExistenteException extends Exception {
    
    public UsuarioExistenteException(){
        super("Usuário já cadastrado!");
    }
}
