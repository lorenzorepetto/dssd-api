package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.SociedadAnonima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SociedadAnonimaRepository extends JpaRepository<SociedadAnonima, Long> {
    Optional<SociedadAnonima> findByNombre(String nombre);
}
