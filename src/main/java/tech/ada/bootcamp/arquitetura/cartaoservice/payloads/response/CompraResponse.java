package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {
    
    private UUID id;
    private Cartao numeroCartao;
    private String loja;
    private BigDecimal valor;
    private LocalDate dataCompra;


}
