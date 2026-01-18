package com.cerofour.MiniGram.user_follows.infrastructure.web;

import com.cerofour.MiniGram.user_follows.application.in.FollowUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class FollowUserController {

    private final FollowUserUseCase followUserUseCase;

    @PostMapping("/{username}/follows")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void follow(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String username
    ) {
        followUserUseCase.follow(user.getUsername(), username);
    }

    @DeleteMapping("/{username}/followers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String username
    ) {
        followUserUseCase.unfollow(user.getUsername(), username);
    }

}
