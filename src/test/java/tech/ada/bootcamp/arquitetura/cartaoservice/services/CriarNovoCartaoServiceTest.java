package tech.ada.bootcamp.arquitetura.cartaoservice.services;


import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Cartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Usuario;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.TipoCartao;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.CartaoRepository;

@ExtendWith(MockitoExtension.class)
public class CriarNovoCartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CriarNovoCartaoService criarNovoCartaoService;

    @DisplayName ("Should Save Successfully A NewCard")
    @Test
    void shouldSaveSuccessfullyANewCard(){
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);

        TipoCartao randomTipoCartao = TipoCartao.values()[(int)Math.random() * TipoCartao.values().length];
        String randomIdentificador = Faker.instance().regexify("[0-9]{11}");
        String randomNome = Faker.instance().name().fullName();

        Mockito.when(cadastroUsuarioRequest.getTipoCartao()).thenReturn(randomTipoCartao);
        
        var usuario = new Usuario();
        usuario.setIdentificador(randomIdentificador);
        usuario.setNome(randomNome);

        criarNovoCartaoService.execute(cadastroUsuarioRequest.getTipoCartao(), usuario);

        ArgumentCaptor<Cartao> cartaoArgumentCaptor = ArgumentCaptor.forClass(Cartao.class);
        Mockito.verify(cartaoRepository,Mockito.times(1))
                .save(cartaoArgumentCaptor.capture());
        Cartao cartaoSalvo = cartaoArgumentCaptor.getValue();
        
        Assertions.assertEquals(LocalDate.now().plusYears(5), cartaoSalvo.getVencimentoCartao());
        Assertions.assertEquals(3,cartaoSalvo.getCodigoSeguranca().length());
        Assertions.assertEquals(12, cartaoSalvo.getNumeroCartao().length());
        Assertions.assertEquals(randomTipoCartao, cartaoSalvo.getTipoCartao());
        Assertions.assertEquals(randomIdentificador, cartaoSalvo.getUsuario().getIdentificador());
        Assertions.assertEquals(randomNome, cartaoSalvo.getNomeTitular());
        Assertions.assertEquals(randomNome, cartaoSalvo.getUsuario().getNome());
        Assertions.assertNotNull(cartaoSalvo.getCreatedAt());

    }

    @DisplayName ("Should Fail To Save New Card With NonExisting User")
    @Test
    void shouldFailToSaveNewCardWithNonExistingUser() {
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);

        TipoCartao randomTipoCartao = TipoCartao.values()[(int)Math.random() * TipoCartao.values().length];
        String randomIdentificador = Faker.instance().regexify("[0-9]{11}");
        String randomNome = Faker.instance().name().fullName();

        Mockito.when(cadastroUsuarioRequest.getTipoCartao()).thenReturn(randomTipoCartao);

        var usuario = new Usuario();
        usuario.setIdentificador(randomIdentificador);
        usuario.setNome(randomNome);

        try {
            criarNovoCartaoService.execute(cadastroUsuarioRequest.getTipoCartao(), usuario);
            Assertions.fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            Assertions.assertEquals("Usuário não encontrado", e.getMessage());
        }

    }

}
