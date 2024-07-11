package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.enums.TipoUsuario;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.AnuncioExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.AuthDTO;
import com.ufrn.imd.ecommerce.models.DTO.AuthenticationDTO;
import com.ufrn.imd.ecommerce.models.DTO.RegisterDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Endereco;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.services.AuthService;
import com.ufrn.imd.ecommerce.services.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;
    @Qualifier("clienteService")
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        String token = "";
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            Usuario usuario = (Usuario) auth.getPrincipal();
            token = tokenService.generateToken(usuario);

            return ResponseEntity.ok(new AuthDTO(token, usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario().getRole()));
        } catch (RuntimeException err){
            return ResponseEntity.badRequest().body(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        String encyptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(data.getEndereco());
        try {
            Usuario usuario = new Usuario(data.getNome(), data.getEmail(), encyptedPassword, data.getTelefone(), 0.0, enderecos, TipoUsuario.COMUM);
            usuarioService.createUsuario(usuario);

            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            usuario = (Usuario) auth.getPrincipal();
            String token = tokenService.generateToken(usuario);

            return ResponseEntity.ok(new AuthDTO(token, usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario().getRole()));
        } catch (UsuarioExCustom err) {
            return ResponseEntity.badRequest().body(err);
        }
    }

}
