package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.dependentes;

import java.util.List;

public record DependenteListaResponse(
    String idDoTitular,
    String nomeDoTitular,
    Integer quantidadeDeDependentes,
    List<DependenteResponse> dependentes) {

}
