package com.cerofour.MiniGram.user.infrastructure.persistence;

import com.cerofour.MiniGram.user.domain.UserProfile;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {

    Page<UserEntity> findAll(@NotNull Pageable pageable);

    /**
     * Busca todos los usuarios cuyo campo 'username' comienza con el prefijo dado.
     * Spring Data traduce este nombre de método a la cláusula SQL 'WHERE username LIKE ?%'
     * * @param prefix El string inicial (ej: "spring")
     * @return Una lista de entidades User coincidentes
     */
    Page<UserEntity> findByUsernameStartingWith(String preffix, Pageable pageable);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Query(value = """
        SELECT
            u.id AS id,
            u.username AS username,
            u.fullname AS fullname,
            u.email AS email,
            u.gender AS gender,
            u.birthdate AS birthdate,
            u.created_at AS createdAt,

            (SELECT COUNT(*)
             FROM user_follows f
             WHERE f.followee_id = u.id) AS followerCount,

            (SELECT COUNT(*)
             FROM user_follows f
             WHERE f.follower_id = u.id) AS followingCount,

            (SELECT COUNT(*)
             FROM user_likes l
             JOIN posts p ON p.id = l.post_id
             WHERE p.user_id = u.id) AS likeCount

        FROM users u
        WHERE u.id = :userId
        """,
            nativeQuery = true)
    UserProfile getUserProfile(@Param("userId") int userId);


    @Modifying // Indica que es un UPDATE/DELETE
    @Transactional
    @Query("UPDATE UserEntity u SET u.fullname = :fullname, u.gender = :gender, u.birthdate = :birthdate WHERE u.id = :id")
    int updateBasicInfo(@Param("id") Integer id,
                        @Param("fullname") String fullname,
                        @Param("gender") Integer gender,
                        @Param("birthdate") LocalDate birthdate);
}
