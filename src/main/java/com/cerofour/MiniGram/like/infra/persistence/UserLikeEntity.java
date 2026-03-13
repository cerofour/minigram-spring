package com.cerofour.MiniGram.like.infra.persistence;

import com.cerofour.MiniGram.post.infrastructure.persistence.PostEntity;
import com.cerofour.MiniGram.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_likes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeEntity {

    @EmbeddedId
    private UserLikeId id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("userId")                    // Mapea al campo 'userId' dentro del @Embeddable
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("postId")                    // Mapea al campo 'postId' dentro del @Embeddable
//    @JoinColumn(name = "post_id")
//    private PostEntity post;

    @Column(name = "like_date")
    private LocalDate likeDate;

    public UserLikeEntity(Integer userId, UUID postId) {
        this.id = new UserLikeId(userId, postId);
        this.likeDate = LocalDate.now();
    }

}