package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {
    private Cartao numeroCartao;
    private String loja;
    private BigDecimal valor;

}
