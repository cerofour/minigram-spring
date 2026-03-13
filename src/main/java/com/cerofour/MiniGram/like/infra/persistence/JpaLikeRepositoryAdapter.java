package com.cerofour.MiniGram.like.infra.persistence;

import com.cerofour.MiniGram.like.application.out.LikeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaLikeRepositoryAdapter implements LikeRepositoryPort {

    private final SpringLikeRepository springLikeRepository;

    @Override
    public void delete(UUID postId, Integer userId) {
        springLikeRepository.deleteById(new UserLikeId(userId, postId));
    }

    @Override
    public boolean exists(UUID postId, Integer userId) {
        return springLikeRepository.existsById(new UserLikeId(userId, postId));
    }

    @Override
    public void save(UUID postId, Integer userId) {
        springLikeRepository.save(new UserLikeEntity(userId, postId));
    }
}
