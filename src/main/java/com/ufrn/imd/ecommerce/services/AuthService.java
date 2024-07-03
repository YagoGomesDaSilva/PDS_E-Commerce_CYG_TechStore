package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UsuarioRepository userRepository;
    private final AnuncianteRepository anuncianteRepository;

    public AuthService(UsuarioRepository userRepository, AnuncianteRepository anuncianteRepository) {
        this.userRepository = userRepository;
        this.anuncianteRepository = anuncianteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return anuncianteRepository.findByEmail(email);
    }




}
