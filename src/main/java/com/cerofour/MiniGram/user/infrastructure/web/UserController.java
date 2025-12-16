package com.cerofour.MiniGram.user.infrastructure.web;

import com.cerofour.MiniGram.user.application.UserService;
import com.cerofour.MiniGram.user.application.in.FindUserUseCase;
import com.cerofour.MiniGram.user.infrastructure.web.dto.UserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final FindUserUseCase findUserUseCase;

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
}
