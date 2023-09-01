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
        Usuario usuario = FakeData.gerarUsuario();
        CadastroUsuarioRequest cadastroUsuarioRequest = createCadastroUsuarioRequest(usuario);
        
        Mockito.when(cartaoService.execute(Mockito.any(), Mockito.any())).thenReturn(new Cartao());
        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        usuarioService.cadastrar(cadastroUsuarioRequest);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.verify(usuarioRepository, Mockito.times(2)).save(usuarioArgumentCaptor.capture());

        assertUser(usuario, usuarioArgumentCaptor.getValue());
        Assertions.assertEquals(usuario.getDependentes().size(), 0);

    }

    @DisplayName("Should create a new user with one new credit card and address and a list of dependents")
    @Test
    public void createUserSucessufullyWithDependents() {
        Usuario usuario = FakeData.gerarUsuario(3);
        CadastroUsuarioRequest cadastroUsuarioRequest = createCadastroUsuarioRequest(usuario);
        
        Mockito.when(cartaoService.execute(Mockito.any(), Mockito.any())).thenReturn(new Cartao());
        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);
        Mockito.when(cartaoService.cadastrarAdicional(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new Cartao());

        usuarioService.cadastrar(cadastroUsuarioRequest);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.verify(usuarioRepository, Mockito.times(2)).save(usuarioArgumentCaptor.capture());

        assertUser(usuario, usuarioArgumentCaptor.getValue());
        Assertions.assertEquals(3, usuarioArgumentCaptor.getValue().getDependentes().size());
    }


    private void assertUser(Usuario expected, Usuario actual) {
        Assertions.assertEquals(expected.getIdentificador(), actual.getIdentificador());
        Assertions.assertEquals(expected.getNome(), actual.getNome());
        Assertions.assertEquals(expected.getDependentes(), actual.getDependentes());
        Assertions.assertEquals(expected.getEndereco(), actual.getEndereco());
        Assertions.assertNotNull(actual.getCreatedAt());
    }

    private CadastroUsuarioRequest createCadastroUsuarioRequest(Usuario usuario) {
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);

        var enderecoRequest = new EnderecoRequest(usuario.getEndereco());

        Mockito.when(cadastroUsuarioRequest.getNome()).thenReturn(usuario.getNome());
        Mockito.when(cadastroUsuarioRequest.getIdentificador()).thenReturn(usuario.getIdentificador());
        Mockito.when(cadastroUsuarioRequest.getEnderecoRequest()).thenReturn(enderecoRequest);
        Mockito.when(cadastroUsuarioRequest.getDependentes()).thenReturn(
            usuario.getDependentes().stream()
                .map(dependente -> new DependenteRequest(dependente.getCpf(), dependente.getNome()))
                .collect(Collectors.toList()));
        
        return cadastroUsuarioRequest;
    }

}
