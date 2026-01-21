package com.cerofour.MiniGram.post.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer userId;

    private String description;

    private String pictureLink;

//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false, nullable = false)
//    private Timestamp createdAt;
}
