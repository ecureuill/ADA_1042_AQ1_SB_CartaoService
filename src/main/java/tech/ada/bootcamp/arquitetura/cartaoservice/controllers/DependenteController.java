package tech.ada.bootcamp.arquitetura.cartaoservice.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.UsuarioNotFoundException;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.DependenteRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.dependentes.DependenteListaResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.dependentes.DependenteResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.services.DependenteService;
import tech.ada.bootcamp.arquitetura.cartaoservice.services.cartao.BuscarCartaoUseCase;
import tech.ada.bootcamp.arquitetura.cartaoservice.services.usuarios.BuscarUsuarioUseCase;

@RestController
@RequestMapping("/dependente")
@Slf4j
@RequiredArgsConstructor
public class DependenteController {

  private final BuscarCartaoUseCase buscarCartao;
  private final DependenteService dependenteService;

  @GetMapping("/{userId}")
  public DependenteListaResponse getDependente(@PathVariable final String userId) {
    try {
      return dependenteService.getDependentes(userId);
    } catch (final UsuarioNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    }
  }

  @PostMapping("/{userId}")
  public ResponseEntity<DependenteResponse> createDependente(
      @RequestBody @Valid DependenteRequest dependenteRequest,
      @PathVariable String userId) {
    try {
      final var cartao = buscarCartao.findByUserId(userId);
      
      final var dependente = dependenteService.createDependente(
          dependenteRequest,
          cartao.getUsuario(),
          cartao.getTipoCartao());

      final var response = new DependenteResponse(dependente);

      return ResponseEntity.status(CREATED).body(response);
    } catch (UsuarioNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    }

  }

}
