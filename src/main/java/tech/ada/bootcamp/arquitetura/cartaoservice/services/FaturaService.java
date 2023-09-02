package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tech.ada.bootcamp.arquitetura.cartaoservice.converter.FaturaConverter;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Fatura;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.FaturaResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CompraRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.FaturaRepository;

@Service
public class FaturaService {

    private Integer generateFaturaXDaysBeforePaymentDate = 10;

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private FaturaConverter faturaConverter;

    public Optional<Fatura> getBillById(Long id) {
        return faturaRepository.findById(id);
    }

    @Scheduled(cron = "0 0 8 * * ?", zone="America/Sao_Paulo")
    public void generateFatura() {
        LocalDate currentDate = LocalDate.now();
        List<Cartao> allCartoes = cartaoRepository.findAll();

        for (Cartao cartao : allCartoes) {
            
            LocalDate nextVencimento = (cartao.getVencimentoCartao() <= currentDate.getDayOfMonth()) ? 
                                         currentDate.plusMonths(1).withDayOfMonth(cartao.getVencimentoCartao()) : 
                                         currentDate.withDayOfMonth(cartao.getVencimentoCartao());
        
            if (nextVencimento.minusDays(generateFaturaXDaysBeforePaymentDate).isEqual(currentDate)) {
                BigDecimal totalValor = compraRepository.sumValorByCartaoNumeroCartao(cartao.getNumeroCartao())
                                                       .orElse(BigDecimal.ZERO);
        
                Fatura fatura = new Fatura();
                fatura.setValor(totalValor);
                fatura.setCartao(cartao);
                faturaRepository.save(fatura);
            }
        }
        
    }

    public Optional<Fatura> getLastFaturaForCartao(String numeroCartao) {
        return faturaRepository.getLastFaturaFromCartao(numeroCartao);
    }

    public List<FaturaResponse> findAllFaturasForUsuarioIdentificador(String usuarioIdentificador) {

        List<Cartao> cartoes = cartaoRepository.findByUsuarioIdentificador(usuarioIdentificador);
        
        List<Fatura> faturas = faturaRepository.findByCartaoIn(cartoes);
        
        return faturas.stream().map(faturaConverter::convertToFaturaResponse).collect(Collectors.toList());
    }
    

    public List<FaturaResponse> findAllFaturasByNumeroCartao(String numeroCartao) {
        Optional<Cartao> optionalCartao = cartaoRepository.findByNumeroCartao(numeroCartao);
        
        if (!optionalCartao.isPresent()) {
            return Collections.emptyList();
        }
        
        Cartao cartao = optionalCartao.get();
        
        List<Fatura> faturas = faturaRepository.findByCartao(cartao);
        
        return faturas.stream()
                      .map(faturaConverter::convertToFaturaResponse)
                      .collect(Collectors.toList());
    }
    

    public FaturaResponse getFaturaForSpecificMonthAndYear(String numeroCartao, int month, int year) {
    Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao)
            .orElseThrow(() -> new EntityNotFoundException("Cartao não encontrado"));

    Fatura fatura = faturaRepository.findByCartaoAndMonthYear(cartao, month, year)
            .orElseThrow(() -> new EntityNotFoundException("Fatura inexistente para o período selecionado"));

    return faturaConverter.convertToFaturaResponse(fatura);
}


    
}

