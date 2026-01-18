package com.cerofour.MiniGram.user_follows.infrastructure.persistence;

import com.cerofour.MiniGram.user.application.out.UserRepositoryPort;
import com.cerofour.MiniGram.user.domain.User;
import com.cerofour.MiniGram.user.domain.exception.UsernameInvalidException;
import com.cerofour.MiniGram.user_follows.application.out.FollowUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserFollowRepositoryAdapter implements FollowUserPort {

    private final UserRepositoryPort userRepositoryPort;
    private final SpringDataFollowRepository springDataFollowRepository;

    @Override
    public boolean exists(String currentUser, String targetUser) {

        User cu = userRepositoryPort.findByUsername(currentUser)
                .orElseThrow(UsernameInvalidException::new);

        User tu = userRepositoryPort.findByUsername(targetUser)
                .orElseThrow(UsernameInvalidException::new);


        return springDataFollowRepository.existsFollow(cu.getId(), tu.getId());
    }

    @Override
    public void follow(String currentUser, String targetUser) {
        User cu = userRepositoryPort.findByUsername(currentUser)
                .orElseThrow(UsernameInvalidException::new);

        User tu = userRepositoryPort.findByUsername(targetUser)
                .orElseThrow(UsernameInvalidException::new);

        springDataFollowRepository.insertFollow(cu.getId(), tu.getId());
    }

    @Override
    public void unfollow(String currentUser, String targetUser) {
        User cu = userRepositoryPort.findByUsername(currentUser)
                .orElseThrow(UsernameInvalidException::new);

        User tu = userRepositoryPort.findByUsername(targetUser)
                .orElseThrow(UsernameInvalidException::new);

        springDataFollowRepository.deleteFollow(cu.getId(), tu.getId());
    }
}
