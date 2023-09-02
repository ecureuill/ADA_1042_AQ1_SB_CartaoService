package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

public record DependenteRequest (
    @CPF @NotBlank @JsonProperty("cpf") String cpf,
    @NotBlank @JsonProperty("nome") String nome
    ) {}
