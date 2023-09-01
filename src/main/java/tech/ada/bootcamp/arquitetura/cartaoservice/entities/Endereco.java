package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.EnderecoRequest;

@Data
@Embeddable
@NoArgsConstructor
public class Endereco {
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private String numero;
    
    public Endereco(EnderecoRequest enderecoRequest) {
        this.cep = enderecoRequest.getCep();
        this.rua = enderecoRequest.getRua();
        this.bairro = enderecoRequest.getBairro();
        this.cidade = enderecoRequest.getCidade();
        this.estado = enderecoRequest.getEstado();
        this.complemento = enderecoRequest.getComplemento();
        this.numero = enderecoRequest.getNumero();
    }
}
