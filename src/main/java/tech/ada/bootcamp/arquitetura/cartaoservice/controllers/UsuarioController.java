package tech.ada.bootcamp.arquitetura.cartaoservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.CadastroUsuarioRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.CadastroUsuarioResponse;
import tech.ada.bootcamp.arquitetura.cartaoservice.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    
    @Transactional
    @PostMapping(path = "", produces = "application/json" )
    public CadastroUsuarioResponse cadastrarUsuario(@RequestBody CadastroUsuarioRequest cadastroUsuarioRequest){
        return usuarioService.cadastrar(cadastroUsuarioRequest);
    }

    // @PostMapping(path = "/dependente/{idUsuario}", produces = "application/json" )
    // public CadastroUsuarioResponse adicionarDependente(@RequestBody CadastroUsuarioRequest cadastroUsuarioRequest, @PathVariable("idUsuario") String idUsuario){
    //     return new CadastroUsuarioResponse();
    // }

}
