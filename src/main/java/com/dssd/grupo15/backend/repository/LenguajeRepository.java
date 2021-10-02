package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Lenguaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LenguajeRepository extends JpaRepository<Lenguaje, Long> {
    Optional<Lenguaje> findByCode(String code);
}
