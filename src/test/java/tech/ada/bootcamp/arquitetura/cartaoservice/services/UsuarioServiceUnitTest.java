package tech.ada.bootcamp.arquitetura.cartaoservice.services;

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
        Mockito.when(cadastroUsuarioRequest.getEnderecoRequest()).thenReturn(enderecoRequest);

        Mockito.when(cartaoService.execute(Mockito.any())).thenReturn(new Cartao());

        usuarioService.cadastrar(cadastroUsuarioRequest);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuarioArgumentCaptor.capture());

        //TODO nao esta retornando o id
        Assertions.assertEquals(usuario, usuarioArgumentCaptor.getValue());

    }
}
