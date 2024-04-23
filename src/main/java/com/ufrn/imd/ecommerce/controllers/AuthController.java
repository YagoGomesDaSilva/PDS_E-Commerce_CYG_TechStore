package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.InfoDuplicatedException;
import com.ufrn.imd.ecommerce.models.DTO.AuthenticationDTO;
import com.ufrn.imd.ecommerce.models.DTO.RegisterDTO;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((UsuarioConcreto) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        String encyptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        UsuarioConcreto usuario = new UsuarioConcreto(data.getEmail(), encyptedPassword, data.getTipoUsuario());

        try {
            this.usuarioService.createUsuario(usuario);
        } catch (InfoDuplicatedException e) {
            return ResponseEntity.badRequest().body("Já existe um usuário cadastrado com esse email!");
        }

        return ResponseEntity.ok().build();
    }
}
