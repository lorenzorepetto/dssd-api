package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    Optional<Pais> findByCode(String code);
}
