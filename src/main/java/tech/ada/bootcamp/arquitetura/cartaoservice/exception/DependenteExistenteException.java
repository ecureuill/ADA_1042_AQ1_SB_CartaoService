package tech.ada.bootcamp.arquitetura.cartaoservice.exception;

public class DependenteExistenteException extends Exception {

    public DependenteExistenteException(String cpf){
        super("Dependente " + cpf + " já cadastrado");
    }
    
}
