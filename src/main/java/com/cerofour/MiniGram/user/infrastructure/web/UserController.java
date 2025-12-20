package com.cerofour.MiniGram.user.infrastructure.web;

import com.cerofour.MiniGram.user.application.UserService;
import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.application.in.UpdateUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.exception.UserNotFoundException;
import com.cerofour.MiniGram.user.infrastructure.web.dto.UpdateUserRequest;
import com.cerofour.MiniGram.user.infrastructure.web.dto.UserResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final FindUserUseCase findUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping("")
    List<UserResult> listAllUsers(
            @PageableDefault(page = 0, size = 20, sort = "created_at", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return findUserUseCase.all(pageable.getPageNumber(), pageable.getPageSize())
                .stream()
                .map(u -> UserResult.builder()
                        .id(u.getId())
                        .fullname(u.getFullname())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .birthdate(u.getBirthdate())
                        .gender(u.getGender().equals((short) 1) ? 'M' : 'F')
                        .createdAt(u.getCreatedAt())
                        .build())
                .toList();
    }

    @PostMapping("/me") // O @PutMapping, pero pediste POST
    public ResponseEntity<User> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails, // Spring Security nos da el usuario actual
            @RequestBody @Valid UpdateUserRequest request
    ) {

        User u = updateUserUseCase.updateUserData(userDetails.getUsername(), request.getFullname(), request.getGender(), request.getBirthdate());

        return ResponseEntity.ok(u);
    }

    // --- 2. Endpoint para subir foto de perfil ---
    @SneakyThrows // Para manejar IOException del archivo brevemente
    @PostMapping(value = "/me/profilePicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProfilePicture(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        updateUserUseCase.updateUserProfilePicture(userDetails.getUsername(), file.getOriginalFilename(), file.getInputStream());

        return ResponseEntity.ok().build();
    }
}
