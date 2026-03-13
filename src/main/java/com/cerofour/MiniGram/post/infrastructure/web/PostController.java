package com.cerofour.MiniGram.post.infrastructure.web;

import com.cerofour.MiniGram.like.application.in.ToggleLikeUseCase;
import com.cerofour.MiniGram.like.domain.LikeResult;
import com.cerofour.MiniGram.post.application.dto.PostWithUserDetails;
import com.cerofour.MiniGram.post.application.in.GetFeedUseCase;
import com.cerofour.MiniGram.post.application.in.GetPostsUseCase;
import com.cerofour.MiniGram.post.application.in.CreatePostUseCase;
import com.cerofour.MiniGram.post.domain.Post;
import com.cerofour.MiniGram.post.infrastructure.PostMapper;
import com.cerofour.MiniGram.post.infrastructure.web.dto.CreatePostRequest;
import com.cerofour.MiniGram.post.infrastructure.web.dto.PostResult;
import com.cerofour.MiniGram.shared.domain.pagination.PaginatedResult;
import com.cerofour.MiniGram.shared.infrastructure.PaginationMapper;
import com.cerofour.MiniGram.user.application.in.GetUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.exception.UsernameInvalidException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostsUseCase getPostsUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetFeedUseCase getFeedUseCase;
    private final ToggleLikeUseCase likePostUseCase;

    @PostMapping("/post")
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

    @PostMapping("/post/{postId}/like")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LikeResult> likePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID postId
    ) {

        User currentUser = getUserUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(UsernameInvalidException::new);

        return ResponseEntity.ok(likePostUseCase.likePost(postId, currentUser.getId()));
    }

    @GetMapping("/user/{username}/posts")
    public ResponseEntity<PaginatedResult<PostResult>> getPostsByUsername(
            @PathVariable String username,
            @PageableDefault(size = 10, sort = "userId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PaginatedResult<Post> posts = getPostsUseCase.getPostsByUsername(
                username,
                PaginationMapper.from(pageable)
        );

        PaginatedResult<PostResult> result = new PaginatedResult<>(
                posts.data().stream().map(PostMapper::toResult).toList(),
                posts.pagination()
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/feed")
    public ResponseEntity<PaginatedResult<PostWithUserDetails>> getFeed(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        User u = getUserUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Unreacheable, @AuthenticationPrincipal instantiated without valid user details??"));

        return ResponseEntity.ok(getFeedUseCase.getFeed(u, PaginationMapper.from(pageable)));
    }
}
