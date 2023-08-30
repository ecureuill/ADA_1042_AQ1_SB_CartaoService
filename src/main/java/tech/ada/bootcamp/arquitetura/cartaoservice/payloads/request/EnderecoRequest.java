package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import lombok.Getter;
import lombok.Setter;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;

@Getter
@Setter
public class EnderecoRequest {    
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private String numero;
    
    public EnderecoRequest() {
    }

    public EnderecoRequest(Endereco endereco) {
        this.cep = endereco.getCep();
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.complemento = endereco.getComplemento();
        this.numero = endereco.getNumero();
    }

}
