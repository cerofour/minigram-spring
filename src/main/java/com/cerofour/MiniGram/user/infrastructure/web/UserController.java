package com.cerofour.MiniGram.user.infrastructure.web;

import com.cerofour.MiniGram.auth.domain.exception.UserNotAuthenticatedException;
import com.cerofour.MiniGram.user.application.in.GetUserUseCase;
import com.cerofour.MiniGram.user.application.in.UpdateUserUseCase;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.UserProfile;
import com.cerofour.MiniGram.user.domain.exception.UserNotFoundException;
import com.cerofour.MiniGram.user.domain.exception.UsernameInvalidException;
import com.cerofour.MiniGram.user.infrastructure.UserMapper;
import com.cerofour.MiniGram.user.infrastructure.web.dto.*;
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

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping("")
    List<UserResult> listAllUsers(
            @PageableDefault(page = 0, size = 20, sort = "created_at", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return getUserUseCase.all(pageable.getPageNumber(), pageable.getPageSize())
                .stream()
                .map(UserMapper::toWebResult)
                .toList();
    }

    //region User Profile Management

    //region My Profile
    @PostMapping("/me")
    public ResponseEntity<Void> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails, // Spring Security nos da el usuario actual
            @RequestBody @Valid UpdateUserRequest request
    ) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResult> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        URL preSignedUrl = getUserUseCase.getUserProfilePicturePreSignedURL(
                getUserUseCase.findByUsername(userDetails.getUsername()).orElseThrow(UsernameInvalidException::new)
        );
        UserProfile currentUser = getUserUseCase.getUserProfile(userDetails.getUsername());

        return ResponseEntity.ok(UserMapper.toMyProfileResult(
                currentUser,
                preSignedUrl
        ));
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

    @GetMapping(value = "/me/profilePicture")
    public ResponseEntity<UserProfilePictureResult> getProfilePicture(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        User currentUser = getUserUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        URL preSignedUrl = getUserUseCase.getUserProfilePicturePreSignedURL(currentUser);

        return ResponseEntity.ok(
                UserProfilePictureResult.builder()
                        .preSignedUrl(preSignedUrl)
                        .build()
        );
    }
    //endregion

    //region Get others users profile

    /**
     * Queries for a specific user profile, different from the currently logged user.
     * @param username target user
     * @return UserProfileResult, with profile picture and only the information a user is allowed to see about others
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResult> getUserProfile(
            @PathVariable() String username
    ) {

        UserProfile userProfile = getUserUseCase.getUserProfile(username);

        return ResponseEntity.ok(UserMapper.toUserProfileResult(
                userProfile,
                getUserUseCase.getUserProfilePicturePreSignedURL(User.builder().id(userProfile.getId()).build())
        ));
    }

    @GetMapping("/{username}/profilePicture")
    public ResponseEntity<UserProfilePictureResult> getUserProfilePicture(
            @PathVariable() String username
    ) {

        User user = getUserUseCase.findByUsername(username)
                .orElseThrow(UsernameInvalidException::new);

        return ResponseEntity.ok(UserProfilePictureResult.builder()
                .preSignedUrl(getUserUseCase.getUserProfilePicturePreSignedURL(user))
                .build()
        );
    }

    //endregion User profile

    //endregion
}
