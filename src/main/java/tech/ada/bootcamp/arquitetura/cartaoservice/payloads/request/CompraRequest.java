package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@Setter
public class CompraRequest {
    private LocalDate dataCompra;
    private String numeroCartao;
    private String loja;
    private BigDecimal valor;

}
