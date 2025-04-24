package com.ejercicio.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "registry_entity")
public class RegistryEntity {
    // add expiration date for JWT & as such, when jwt expires, active will become
    // null
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String UUIDId;
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String name;
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneEntity> phones;
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private LocalDateTime modified;
    @Column(nullable = false)
    private LocalDateTime last_login;
    @Column(nullable = false)
    private Boolean isactive;
    @Column(unique = true)
    private String token;
    @Column(nullable = false)
    private Date expireDate;

}
