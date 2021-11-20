package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Exportacion;
import com.dssd.grupo15.backend.model.Pais;
import com.dssd.grupo15.backend.model.SociedadAnonima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ExportacionRepository extends JpaRepository<Exportacion, Long> {

    // paises a donde más se exporta
    @Query(nativeQuery = true,
            value = "SELECT e.pais_id, p.name, p.code, COUNT(e.pais_id) as cant " +
                    "FROM exportaciones e " +
                    "INNER JOIN paises p ON e.pais_id = p.id " +
                    "GROUP BY e.pais_id, p.name, p.code ORDER BY cant DESC LIMIT 5")
    List<Map<String, Object>> paisesConMasExportaciones();
}