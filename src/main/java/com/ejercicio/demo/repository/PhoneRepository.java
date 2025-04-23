package com.ejercicio.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejercicio.demo.entity.PhoneEntity;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, String> {
    PhoneEntity findByNumberAndCountrycodeAndCitycode(String phone, String cityCode, String countryCode);
}
