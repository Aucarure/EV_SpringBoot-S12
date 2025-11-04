package com.tecsup.exam12.security;

import com.tecsup.exam12.entity.Usuario;
import com.tecsup.exam12.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Buscando usuario: " + username);

        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> {
                    System.out.println("❌ Usuario no encontrado: " + username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        System.out.println("✅ Usuario encontrado: " + usuario.getNombreUsuario());
        System.out.println("👤 Rol: " + usuario.getRol());
        System.out.println("📊 Estado: " + usuario.getEstado());
        System.out.println("🔐 Contraseña: " + usuario.getContrasena()); // ✅ SIN substring

        if (!usuario.getEstado()) {
            System.out.println("⛔ Usuario inactivo: " + username);
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        UserDetails userDetails = User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getContrasena())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        System.out.println("✅ UserDetails creado correctamente para: " + username);

        return userDetails;
    }
}