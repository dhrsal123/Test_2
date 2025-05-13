package com.ejercicio.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class RegistryEntity extends BaseEntity {

    @Column(nullable = false, unique = true, updatable = false)
    private String UUID;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String name;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    @Email(message = "Email deformado",regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(unique = true)
    private String token;

    @Column(nullable = false)
    private Date expireDate;

    @Column(nullable = false, updatable = false)
    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneEntity> phones;

}
