package com.cerofour.MiniGram.post.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPostRepository extends JpaRepository<PostEntity, UUID> {
}
