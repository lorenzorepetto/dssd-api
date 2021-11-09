package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.SociedadAnonima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SociedadAnonimaRepository extends JpaRepository<SociedadAnonima, Long> {
    Optional<SociedadAnonima> findByNombre(String nombre);

    @Query("FROM SociedadAnonima sa INNER JOIN sa.status s " +
            "WHERE s.status = :status AND s.dateCreated = " +
            "(SELECT max(sub_s.dateCreated) FROM SociedadAnonima sub_sa INNER JOIN sub_sa.status sub_s)")
    List<SociedadAnonima> findByStatus(String status);

    List<SociedadAnonima> findByUsername(String username);

    @Query("FROM SociedadAnonima sa INNER JOIN sa.expediente e " +
            "WHERE e.id = :id ")
    SociedadAnonima findByExpedienteId(Long id);
}
