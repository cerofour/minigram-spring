package com.cerofour.MiniGram.user_follows.infrastructure.persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataFollowRepository
        extends org.springframework.data.repository.Repository<UserFollows, UserFollowId> {

    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM user_follows
            WHERE follower_id = :followerId
              AND followee_id = :followeeId
        )
        """, nativeQuery = true)
    boolean existsFollow(
            @Param("followerId") int followerId,
            @Param("followeeId") int followeeId
    );

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO user_follows (follower_id, followee_id)
        VALUES (:followerId, :followeeId)
        ON CONFLICT DO NOTHING
        """, nativeQuery = true)
    void insertFollow(
            @Param("followerId") int followerId,
            @Param("followeeId") int followeeId
    );

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM user_follows
        WHERE follower_id = :followerId
          AND followee_id = :followeeId
        """, nativeQuery = true)
    void deleteFollow(
            @Param("followerId") int followerId,
            @Param("followeeId") int followeeId
    );

}
