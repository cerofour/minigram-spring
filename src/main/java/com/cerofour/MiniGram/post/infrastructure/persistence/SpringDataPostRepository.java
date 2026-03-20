package com.cerofour.MiniGram.post.infrastructure.persistence;

import com.cerofour.MiniGram.post.application.dto.PostWithUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SpringDataPostRepository extends JpaRepository<PostEntity, UUID> {

    Page<PostEntity> findByUserId(Integer userId, Pageable p);

    @Query(
            value = """
                SELECT
                    p.id                        AS id,
                    p.picture_link              AS pictureLink,
                    p.description               AS description,
                    p.user_id                   AS userId,
                    u.username                  AS username,
                    ''                          AS userProfilePictureLink,
                    p.created_at::timestamptz   AS createdAt,
                    COUNT(ul.user_id)           AS likeCount,
                    0                           AS commentCount,
                    BOOL_OR(ul.user_id = :userId) AS liked
                FROM user_follows f
                JOIN posts p
                    ON p.user_id = f.followee_id
                JOIN users u
                    ON u.id = p.user_id
                LEFT JOIN user_likes ul
                    ON ul.post_id = p.id
                WHERE f.follower_id = :userId
                GROUP BY
                    p.id,
                    p.picture_link,
                    p.description,
                    p.user_id,
                    u.username,
                    p.created_at
                """,
            countQuery = """
                SELECT COUNT(*)
                FROM user_follows f
                JOIN posts p
                    ON p.user_id = f.followee_id
                WHERE f.follower_id = :userId
                """,
            nativeQuery = true
    )
    Page<PostWithUserDetails> getFeedForUserWithId(
            @Param("userId") Integer userId,
            Pageable pageable
    );
}
