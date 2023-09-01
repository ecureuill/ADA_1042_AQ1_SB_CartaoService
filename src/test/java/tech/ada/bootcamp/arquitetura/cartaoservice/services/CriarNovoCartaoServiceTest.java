package tech.ada.bootcamp.arquitetura.cartaoservice.services;


import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

    private static final int RANDOM_INDEX = (int) Math.random() * TipoCartao.values().length;
    private static final String RANDOM_IDENTIFICADOR = Faker.instance().regexify("[0-9]{11}");
    private static final String RANDOM_NOME = Faker.instance().name().fullName();
    private static final TipoCartao RANDOM_TIPO_CARTAO = TipoCartao.values()[RANDOM_INDEX];

    @Test
    @DisplayName("Should save successfully a new card")
    void shouldSaveSuccessfullyANewCard() {
        CadastroUsuarioRequest cadastroUsuarioRequest = Mockito.mock(CadastroUsuarioRequest.class);
        Mockito.when(cadastroUsuarioRequest.getTipoCartao()).thenReturn(RANDOM_TIPO_CARTAO);

        var usuario = createUsuario();

        criarNovoCartaoService.execute(cadastroUsuarioRequest.getTipoCartao(), usuario);

        ArgumentCaptor<Cartao> cartaoArgumentCaptor = ArgumentCaptor.forClass(Cartao.class);
        Mockito.verify(cartaoRepository, Mockito.times(1)).save(cartaoArgumentCaptor.capture());

        Cartao cartaoSalvo = cartaoArgumentCaptor.getValue();

        assertCartao(cartaoSalvo);
    }

    private Usuario createUsuario() {
        var usuario = new Usuario();
        usuario.setIdentificador(RANDOM_IDENTIFICADOR);
        usuario.setNome(RANDOM_NOME);
        return usuario;
    }

    private void assertCartao(Cartao cartaoSalvo) {
        Assertions.assertEquals(LocalDate.now().plusYears(5), cartaoSalvo.getVencimentoCartao());
        Assertions.assertEquals(3, cartaoSalvo.getCodigoSeguranca().length());
        Assertions.assertEquals(12, cartaoSalvo.getNumeroCartao().length());
        Assertions.assertEquals(RANDOM_TIPO_CARTAO, cartaoSalvo.getTipoCartao());
        Assertions.assertEquals(RANDOM_IDENTIFICADOR, cartaoSalvo.getUsuario().getIdentificador());
        Assertions.assertEquals(RANDOM_NOME, cartaoSalvo.getNomeTitular());
        Assertions.assertEquals(RANDOM_NOME, cartaoSalvo.getUsuario().getNome());
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
