package com.cerofour.MiniGram.configuration;

import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.domain.exception.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final FindUserUseCase findUserUseCase;

    @NotNull
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        var user = findUserUseCase.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username: " + username));

        // Create a single authority from the "rol" column
        //var authorities = List.of(new SimpleGrantedAuthority(usuario.getRol()));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                //.authorities(authorities)
                .disabled(false)
                .build();
    }
}
