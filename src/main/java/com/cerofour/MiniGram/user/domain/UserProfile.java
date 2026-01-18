package com.cerofour.MiniGram.user.domain;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private Integer gender;
    private Date birthdate;
    private Timestamp createdAt;

    // socials

    private Long followerCount;
    private Long followingCount;
    private Long likeCount;
}
