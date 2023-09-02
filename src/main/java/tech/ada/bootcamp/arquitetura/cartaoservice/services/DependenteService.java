package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.DependenteExistenteException;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.UsuarioNotFoundException;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.TipoCartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.DependenteRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.dependentes.DependenteListaResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.dependentes.DependenteResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.DependenteRepository;

@Service
@RequiredArgsConstructor
public class DependenteService {

  private final DependenteRepository repository;
  private final CriarNovoCartaoService cartaoService;

  public DependenteListaResponse getDependentes(final String userId) {
    final var dependentes = repository
        .findByUsuarioIdentificador(userId);

    if (dependentes.isEmpty()) {
      throw new UsuarioNotFoundException("Usuário não encontrado ou sem dependentes");
    }

    final var result = dependentes
        .stream().map(DependenteResponse::new)
        .collect(Collectors.toList());

    return new DependenteListaResponse(
        userId,
        result.get(0).nome(),
        result.size(),
        result);
  }

  public Dependente createDependente(
      final DependenteRequest request,
      final Usuario user,
      final TipoCartao tipoCartao) {
   final var cartao = cartaoService.cadastrarAdicional(
        request.nome(),
        tipoCartao,
        user);

    final var dependente = new Dependente(request);
    dependente.setCartao(cartao);
    dependente.setUsuario(user);

    return repository.save(dependente);
  }

  public List<Dependente> criarDependentes(
      final CadastroUsuarioRequest cadastroUsuarioRequest,
      final Usuario usuario)
          throws DependenteExistenteException {

    List<Dependente> dependentes = new ArrayList<Dependente>();
    for (DependenteRequest dependenteRequest : cadastroUsuarioRequest.getDependentes()) {
      validarDependente(dependenteRequest);
      Dependente dependente = createDependente(
              dependenteRequest,
              usuario,
              cadastroUsuarioRequest.getTipoCartao());
      dependentes.add(dependente);
    }

    return dependentes;
  }

  private void validarDependente(DependenteRequest dependenteRequest)
      throws DependenteExistenteException {
    // TODO como testar dependentes salvos durante a transaction?
    if (repository.existsByCpf(dependenteRequest.cpf())) {
      throw new DependenteExistenteException(dependenteRequest.cpf());
    }
  }

}