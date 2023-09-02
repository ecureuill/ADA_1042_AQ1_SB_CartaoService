package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.dependentes;

import java.util.UUID;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;

public record DependenteResponse(
        UUID id,
        String nome,
        String cpf,
        String cartao) {

    public DependenteResponse(Dependente dependente) {
        this(
                dependente.getId(),
                dependente.getNome(),
                dependente.getCpf(),
                dependente.getCartao().getNumeroCartao());
    }
}
