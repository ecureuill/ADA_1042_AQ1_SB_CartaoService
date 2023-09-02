package tech.ada.bootcamp.arquitetura.cartaoservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.ada.bootcamp.arquitetura.cartaoservice.converter.FaturaConverter;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Fatura;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.FaturaResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.services.FaturaService;

@RestController
@RequestMapping("/fatura")
public class FaturaController {
    @Autowired
    private FaturaService faturaService;

    @Autowired
    FaturaConverter faturaConverter;


    @GetMapping("/{numeroCartao}/latest")
    public ResponseEntity<FaturaResponse> getLastFaturaByNumeroCartao(@PathVariable String numeroCartao) {

        Optional<Fatura> faturaOpt = faturaService.getLastFaturaForCartao(numeroCartao);
    
        if (!faturaOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
    
        FaturaResponse latestFatura = faturaConverter.convertToFaturaResponse(faturaOpt.get());
    
        return ResponseEntity.ok(latestFatura);
    }


    @GetMapping("/{numeroCartao}")
    public ResponseEntity<List<FaturaResponse>> getAllFaturasByNumeroCartao(@PathVariable String numeroCartao) {

        List<FaturaResponse> faturas = faturaService.findAllFaturasByNumeroCartao(numeroCartao);

        return ResponseEntity.ok(faturas);
    }

    @GetMapping("/usuario/{usuarioIdentificador}")
    public ResponseEntity<List<FaturaResponse>> getAllFaturasForUsuario(@PathVariable String usuarioIdentificador) {

        List<FaturaResponse> faturas = faturaService.findAllFaturasForUsuarioIdentificador(usuarioIdentificador);

        return ResponseEntity.ok(faturas);
    }

    @GetMapping("/{numeroCartao}/{year}/{month}")
    public ResponseEntity<?> getFaturaByNumeroCartaoForSpecificMonthAndYear(@PathVariable String numeroCartao, @PathVariable int year, @PathVariable int month) {
        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().body("O mês informado é inválido");
        }

        FaturaResponse fatura = faturaService.getFaturaForSpecificMonthAndYear(numeroCartao, month, year);
        
        if (fatura == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fatura não encontrada");
        }

        return ResponseEntity.ok(fatura);

    }
}
