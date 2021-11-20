package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Continente;
import com.dssd.grupo15.backend.model.Exportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContinenteRepository extends JpaRepository<Continente, Long> {
    Optional<Continente> findByCode(String code);

    // continente a donde más se exporta exceptuando america
    @Query("SELECT c FROM Exportacion e INNER JOIN e.pais p " +
            "INNER JOIN p.continent c WHERE c.code NOT IN ('SA', 'NA') " +
            "GROUP BY c ORDER BY COUNT(c) DESC")
    List<Continente> continentesConMasExportacionesExceptoAmerica();
}

