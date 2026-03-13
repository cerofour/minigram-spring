package com.cerofour.MiniGram.like.application.out;

import java.util.UUID;

public interface LikeRepositoryPort {
    void delete(UUID postId, Integer userId);
    boolean exists(UUID postId, Integer userId);
    void save(UUID postId, Integer userId);
}
