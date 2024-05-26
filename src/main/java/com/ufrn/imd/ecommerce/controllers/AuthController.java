package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.AnuncioExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.AuthDTO;
import com.ufrn.imd.ecommerce.models.DTO.AuthenticationDTO;
import com.ufrn.imd.ecommerce.models.DTO.RegisterDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.AuthService;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AnuncianteService anuncianteService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        String token = "";
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            token = tokenService.generateToken((UsuarioConcreto) auth.getPrincipal());
            anuncianteService.findByEmail(data.getEmail());
            return ResponseEntity.ok(new AuthDTO(token, "anunciante"));
        } catch (AnuncioExCustom err) {
            return ResponseEntity.ok(new AuthDTO(token, "cliente"));
        } catch (RuntimeException err){
            return ResponseEntity.badRequest().body(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        String encyptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        UsuarioConcreto usuario = new UsuarioConcreto(data.getEmail(), encyptedPassword, data.getTipoUsuario());

        try {
            this.usuarioService.createUsuario(usuario);
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((UsuarioConcreto) auth.getPrincipal());
            return ResponseEntity.ok(token);
        } catch (UsuarioExCustom err) {
            return ResponseEntity.badRequest().body(err);
        }
    }

}
