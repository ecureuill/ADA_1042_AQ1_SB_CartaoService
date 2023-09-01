package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CompraRequest {
    private LocalDate dataCompra;
    private String numeroCartao;
    private String loja;
    private BigDecimal valor;

}
