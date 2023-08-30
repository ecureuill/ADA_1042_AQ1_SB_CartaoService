package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Endereco;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.CadastroUsuarioResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    // private final CriarNovoCartaoService cartaoService;

    public CadastroUsuarioResponse cadastrar(CadastroUsuarioRequest cadastroUsuarioRequest) {
        var enderecoRequest = cadastroUsuarioRequest.getEnderecoRequest();
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoRequest.getRua());
        endereco.setNumero(enderecoRequest.getNumero());
        endereco.setComplemento(enderecoRequest.getComplemento());
        endereco.setCidade(enderecoRequest.getCidade());
        endereco.setEstado(enderecoRequest.getEstado());
        endereco.setCep(enderecoRequest.getCep());
        endereco.setBairro(enderecoRequest.getBairro());

        Usuario usuario = new Usuario(
            cadastroUsuarioRequest.getIdentificador(),
            cadastroUsuarioRequest.getNome(), 
            endereco
        );

        usuarioRepository.save(usuario);
        
        Cartao cartao = new Cartao(); //TO-DO cartaoService.execute(cadastroUsuarioRequest.getTipoCartao(), usuario);
        
        return new CadastroUsuarioResponse(cartao.getNumeroCartao(),cartao.getNomeTitular(), cartao.getTipoCartao(), usuario.getNome());

    }
    
}
