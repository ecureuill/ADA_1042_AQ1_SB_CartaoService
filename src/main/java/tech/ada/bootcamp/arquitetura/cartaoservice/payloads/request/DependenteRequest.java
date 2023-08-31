package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;

public record DependenteRequest(
    @CPF
    @NotBlank
    String cpf,
    @NotBlank
    String nome
) {
    
}
