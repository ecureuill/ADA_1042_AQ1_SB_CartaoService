package tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FaturaResponse {
    private double valor;
    private LocalDate dataFaturaGerada;
    private LocalDate referenciaFatura;
    
    private LocalDate dataVencimento;
    private LocalDate dataProcessamento;
    private BigDecimal valorPago;
    private String numeroCartao;
    
    private List<CompraResponse> resumoCompra;

}
