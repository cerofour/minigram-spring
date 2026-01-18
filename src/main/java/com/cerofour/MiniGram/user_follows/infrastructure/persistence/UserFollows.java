package com.cerofour.MiniGram.user_follows.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user_follows")
@IdClass(UserFollowId.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserFollows {

    @Id
    @Column(name = "follower_id")
    private Integer followerId;

    @Id
    @Column(name = "followee_id")
    private Integer followeeId;
}

