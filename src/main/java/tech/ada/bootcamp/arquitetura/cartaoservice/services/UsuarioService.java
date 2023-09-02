package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Dependente;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.UsuarioExistenteException;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.CadastroUsuarioResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CriarNovoCartaoService cartaoService;

    public CadastroUsuarioResponse cadastrar(CadastroUsuarioRequest cadastroUsuarioRequest)
            throws UsuarioExistenteException {

        if (usuarioRepository.existsById(cadastroUsuarioRequest.getIdentificador())) {
            throw new UsuarioExistenteException();
        }

        Usuario usuario = criarUsuario(cadastroUsuarioRequest);

        usuario = usuarioRepository.save(usuario);

        Cartao cartao = cartaoService.execute(cadastroUsuarioRequest.getTipoCartao(), usuario);

        List<Dependente> dependentes = criarDependentes(cadastroUsuarioRequest, usuario);
        usuario.setDependentes(dependentes);
        usuarioRepository.save(usuario);

        return new CadastroUsuarioResponse(cartao.getNumeroCartao(), cartao.getNomeTitular(), cartao.getTipoCartao(),
                usuario.getNome());

    }

    private List<Dependente> criarDependentes(CadastroUsuarioRequest cadastroUsuarioRequest, Usuario usuario) {
        return cadastroUsuarioRequest.getDependentes().stream().map(dependente -> {
            Cartao cartaoAdicional = cartaoService.cadastrarAdicional(dependente.getNome(),
                    cadastroUsuarioRequest.getTipoCartao(), usuario);
            return new Dependente(null, dependente.getCpf(), dependente.getNome(), usuario, cartaoAdicional,
                    LocalDateTime.now());
        }).collect(Collectors.toList());
    }

    private Usuario criarUsuario(CadastroUsuarioRequest cadastroUsuarioRequest) {
        Endereco endereco = new Endereco(cadastroUsuarioRequest.getEnderecoRequest());

        Usuario usuario = new Usuario(
                cadastroUsuarioRequest.getIdentificador(),
                cadastroUsuarioRequest.getNome(),
                endereco,
                LocalDateTime.now(),
                new ArrayList<Dependente>());
        return usuario;
    }

}
