package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;

@Getter
@Setter
public class EnderecoRequest {   
    @NotBlank
    @Pattern(regexp = "[0-9]{8}")
    private String cep;
    @NotBlank
    private String rua;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{2}")
    private String estado;
    private String complemento;
    @NotBlank
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
