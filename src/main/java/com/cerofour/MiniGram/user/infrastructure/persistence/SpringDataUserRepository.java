package com.cerofour.MiniGram.user.infrastructure.persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {

    Page<UserEntity> findAll(Pageable pageable);

    /**
     * Busca todos los usuarios cuyo campo 'username' comienza con el prefijo dado.
     * Spring Data traduce este nombre de método a la cláusula SQL 'WHERE username LIKE ?%'
     * * @param prefix El string inicial (ej: "spring")
     * @return Una lista de entidades User coincidentes
     */
    Page<UserEntity> findByUsernameStartingWith(String preffix, Pageable pageable);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Modifying // Indica que es un UPDATE/DELETE
    @Transactional
    @Query("UPDATE UserEntity u SET u.fullname = :fullname, u.gender = :gender, u.birthdate = :birthdate WHERE u.id = :id")
    int updateBasicInfo(@Param("id") Integer id,
                        @Param("fullname") String fullname,
                        @Param("gender") Integer gender,
                        @Param("birthdate") LocalDate birthdate);
}
