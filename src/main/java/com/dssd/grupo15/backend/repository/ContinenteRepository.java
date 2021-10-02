package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Continente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContinenteRepository extends JpaRepository<Continente, Long> {
    Optional<Continente> findByCode(String code);
}
