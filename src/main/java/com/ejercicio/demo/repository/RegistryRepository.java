package com.ejercicio.demo.repository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejercicio.demo.entity.RegistryEntity;

@Repository
public interface RegistryRepository extends JpaRepository<RegistryEntity, Long> {
    Boolean existsByEmail(String email);
    RegistryEntity findByEmail(@NotNull @Valid String email);
}
