package com.ejercicio.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntityListeners.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created=LocalDateTime.now();

    @Column(nullable = true)
    @UpdateTimestamp
    private LocalDateTime modified;

    @Column(nullable = true)
    private LocalDateTime lastLogin;
}
