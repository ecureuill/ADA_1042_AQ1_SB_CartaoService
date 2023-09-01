package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.DependenteRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.EnderecoRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.UsuarioRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.utils.FakeData;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceUnitTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CriarNovoCartaoService cartaoService;

    @InjectMocks
    private UsuarioService usuarioService;
    
    @DisplayName("Should create a new user with one new credit card and address")
    @Test
    public void createUserSucessufully() {
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);
        Usuario usuario = FakeData.gerarUsuario();
        
        var enderecoRequest = new EnderecoRequest(usuario.getEndereco());

        Mockito.when(cadastroUsuarioRequest.getNome()).thenReturn(usuario.getNome());
        Mockito.when(cadastroUsuarioRequest.getIdentificador()).thenReturn(usuario.getIdentificador());

        Mockito.when(cadastroUsuarioRequest.getEnderecoRequest()).thenReturn(enderecoRequest);

        Mockito.when(cartaoService.execute(Mockito.any(), Mockito.any())).thenReturn(new Cartao());

        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        usuarioService.cadastrar(cadastroUsuarioRequest);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.verify(usuarioRepository, Mockito.times(2)).save(usuarioArgumentCaptor.capture());

        Assertions.assertEquals(usuario.getIdentificador(), usuarioArgumentCaptor.getValue().getIdentificador());        
        Assertions.assertEquals(usuario.getNome(), usuarioArgumentCaptor.getValue().getNome());
        Assertions.assertEquals(usuario.getDependentes(), usuarioArgumentCaptor.getValue().getDependentes());
        Assertions.assertEquals(usuario.getEndereco(), usuarioArgumentCaptor.getValue().getEndereco());
        Assertions.assertEquals(usuario.getDependentes().size(), usuarioArgumentCaptor.getValue().getDependentes().size());
        Assertions.assertNotNull(usuarioArgumentCaptor.getValue().getCreatedAt());

    }

    @DisplayName("Should create a new user with one new credit card and address and a list of dependents")
    @Test
    public void createUserSucessufullyWithDependents() {
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);
        Usuario usuario = FakeData.gerarUsuario(3);
        
        var enderecoRequest = new EnderecoRequest(usuario.getEndereco());

        Mockito.when(cadastroUsuarioRequest.getNome()).thenReturn(usuario.getNome());
        Mockito.when(cadastroUsuarioRequest.getIdentificador()).thenReturn(usuario.getIdentificador());
        Mockito.when(cadastroUsuarioRequest.getEnderecoRequest()).thenReturn(enderecoRequest);
        Mockito.when(cadastroUsuarioRequest.getDependentes()).thenReturn(usuario.getDependentes().stream()
                .map(dependente -> new DependenteRequest(dependente.getCpf(), dependente.getNome()))
                .collect(Collectors.toList()));

        Mockito.when(cartaoService.execute(Mockito.any(), Mockito.any())).thenReturn(new Cartao());
        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);
        Mockito.when(cartaoService.cadastrarAdicional(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new Cartao());

        usuarioService.cadastrar(cadastroUsuarioRequest);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.verify(usuarioRepository, Mockito.times(2)).save(usuarioArgumentCaptor.capture());

        Assertions.assertEquals(usuario.getIdentificador(), usuarioArgumentCaptor.getValue().getIdentificador());        
        Assertions.assertEquals(usuario.getNome(), usuarioArgumentCaptor.getValue().getNome());
        Assertions.assertEquals(usuario.getDependentes(), usuarioArgumentCaptor.getValue().getDependentes());
        Assertions.assertEquals(usuario.getEndereco(), usuarioArgumentCaptor.getValue().getEndereco());
        Assertions.assertNotNull(usuarioArgumentCaptor.getValue().getCreatedAt());
        Assertions.assertEquals(3, usuarioArgumentCaptor.getValue().getDependentes().size());
    }
}
