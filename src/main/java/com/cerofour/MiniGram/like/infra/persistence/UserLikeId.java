package com.cerofour.MiniGram.like.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
public class UserLikeId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "post_id")
    private UUID postId;

    // Constructor vacío requerido por JPA
    protected UserLikeId() {}

    public UserLikeId(Integer userId, UUID postId) {
        this.userId = userId;
        this.postId = postId;
    }

    // equals y hashCode son OBLIGATORIOS en toda PK compuesta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLikeId that)) return false;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }

    // Getters
    public Integer getUserId() { return userId; }
    public UUID getPostId() { return postId; }
}