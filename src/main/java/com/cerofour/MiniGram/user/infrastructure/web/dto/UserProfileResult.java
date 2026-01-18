package com.cerofour.MiniGram.user.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.sql.Date;

@Builder
@Getter
public class UserProfileResult {
    private String username;
    private String fullname;
    private Character gender;
    private Date birthdate;
    private URL profilePicturePreSignedURL;

    private Long followerCount;
    private Long followingCount;
    private Long likeCount;
}
