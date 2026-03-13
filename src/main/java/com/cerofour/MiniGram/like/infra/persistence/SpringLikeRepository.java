package com.cerofour.MiniGram.like.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringLikeRepository extends JpaRepository<UserLikeEntity, UserLikeId> {

}
