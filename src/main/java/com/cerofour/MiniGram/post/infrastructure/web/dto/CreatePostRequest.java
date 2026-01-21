package com.cerofour.MiniGram.post.infrastructure.web.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePostRequest {
    @Size(max = 255, message = "Post description can't exceed 255 characters")
    private String description;
}
