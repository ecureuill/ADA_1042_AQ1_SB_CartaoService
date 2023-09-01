package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.TipoCartao;

@Getter
@Setter
public class CadastroUsuarioRequest {
    @CPF
    @NotBlank
    private String identificador;
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EnderecoRequest enderecoRequest;
    @NotBlank
    private TipoCartao tipoCartao;
    @Valid
    private List<DependenteRequest> dependentes;

}
