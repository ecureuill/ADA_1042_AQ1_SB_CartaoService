package tech.ada.bootcamp.arquitetura.exception;

public class UsuarioExistenteException extends Exception {
    
    public UsuarioExistenteException(){
        super("Usuário já cadastrado!");
    }
}
