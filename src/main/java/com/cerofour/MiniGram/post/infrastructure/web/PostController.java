package com.cerofour.MiniGram.post.infrastructure.web;

import com.cerofour.MiniGram.post.application.in.CreatePostUseCase;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.post.infrastructure.web.dto.CreatePostRequest;
import com.cerofour.MiniGram.user.application.in.GetUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.exception.UsernameInvalidException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users/posts")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetUserUseCase getUserUseCase;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("data") @Valid CreatePostRequest request,
            @RequestPart("image") MultipartFile postImage
    ) throws IOException {

        User currentUser = getUserUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(UsernameInvalidException::new);

        createPostUseCase.createPost(
                Post.builder()
                        .userId(currentUser.getId())
                        .description(request.getDescription())
                        .build(),
                postImage.getOriginalFilename(),
                postImage.getInputStream());

        return ResponseEntity.ok().build();
    }

}
