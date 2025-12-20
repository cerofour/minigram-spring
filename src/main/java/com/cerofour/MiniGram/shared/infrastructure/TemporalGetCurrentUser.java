package com.cerofour.MiniGram.shared.infrastructure;

import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TemporalGetCurrentUser {

    private final FindUserUseCase findUserUseCase;

    public Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        String username = auth.getName(); // normalmente el email o username
        return findUserUseCase.findByUsername(username);
    }
}
