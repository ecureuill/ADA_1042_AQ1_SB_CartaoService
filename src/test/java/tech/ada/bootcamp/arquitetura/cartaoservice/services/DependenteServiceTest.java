package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.DependenteExistenteException;
import tech.ada.bootcamp.arquitetura.cartaoservice.exception.UsuarioExistenteException;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.DependenteRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.EnderecoRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.DependenteRepository;
import tech.ada.bootcamp.arquitetura.cartaoservice.utils.FakeData;

@ExtendWith(MockitoExtension.class)
public class DependenteServiceTest {
    
    @Mock
    private DependenteRepository dependenteRepository;
    @Mock
    private CriarNovoCartaoService cartaoService;

    @InjectMocks
    private DependenteService dependenteService;

    @DisplayName("Should throw error if dependent arealdy exist")
    @Test
    public void shouldThrowErrorIfDependentAlreadyExist() throws UsuarioExistenteException, DependenteExistenteException {
    
        Usuario usuario = FakeData.gerarUsuario(3);    
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);
        
        Mockito.when(cadastroUsuarioRequest.getDependentes()).thenReturn(
            usuario.getDependentes().stream()
                .map(dependente -> new DependenteRequest(dependente.getCpf(), dependente.getNome()))
                .collect(Collectors.toList()));

        Mockito.when(cartaoService.cadastrarAdicional(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new Cartao());
        Mockito.when(cartaoService.cadastrarAdicional(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new Cartao());
        Mockito.when(dependenteRepository.existsByCpf(Mockito.anyString())).thenReturn(false);
        Mockito.when(dependenteRepository.existsByCpf(usuario.getDependentes().get(2).getCpf())).thenReturn(true);

        Assertions.assertThrows(DependenteExistenteException.class, () -> dependenteService.criarDependentes(cadastroUsuarioRequest, usuario));
    }
}