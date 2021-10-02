package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.rest.request.ContinenteDTO;
import com.dssd.grupo15.backend.dto.rest.request.LenguajeDTO;
import com.dssd.grupo15.backend.dto.rest.request.PaisDTO;
import com.dssd.grupo15.backend.model.Continente;
import com.dssd.grupo15.backend.model.Lenguaje;
import com.dssd.grupo15.backend.model.Pais;
import com.dssd.grupo15.backend.repository.ContinenteRepository;
import com.dssd.grupo15.backend.repository.LenguajeRepository;
import com.dssd.grupo15.backend.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaisService {

    private final ContinenteRepository continenteRepository;
    private final LenguajeRepository lenguajeRepository;
    private final PaisRepository paisRepository;

    @Autowired
    public PaisService(ContinenteRepository continenteRepository, LenguajeRepository lenguajeRepository, PaisRepository paisRepository) {
        this.continenteRepository = continenteRepository;
        this.lenguajeRepository = lenguajeRepository;
        this.paisRepository = paisRepository;
    }

    public Pais handleCreation(PaisDTO paisDTO) {
        Optional<Pais> paisOptional = this.paisRepository.findByCode(paisDTO.getCode());
        if (paisOptional.isPresent()) {
            return paisOptional.get();
        }

        // Hay que crearlo
        Pais newPais = new Pais();
        newPais.setCode(paisDTO.getCode());
        newPais.setName(paisDTO.getName());

        // Languages
        List<Lenguaje> lenguajes = new ArrayList<>();
        for (LenguajeDTO lenguajeDTO : paisDTO.getLanguages()) {
            Optional<Lenguaje> lenguajeOptional = this.lenguajeRepository.findByCode(lenguajeDTO.getCode());
            lenguajes.add(lenguajeOptional.orElseGet(() -> this.lenguajeRepository.save(new Lenguaje(lenguajeDTO.getCode(), lenguajeDTO.getName()))));
        }
        newPais.setLanguages(lenguajes);

        // Continent
        ContinenteDTO continenteDTO = paisDTO.getContinent();
        Optional<Continente> continenteOptional = this.continenteRepository.findByCode(continenteDTO.getCode());
        newPais.setContinent(continenteOptional.orElseGet(() -> this.continenteRepository.save(new Continente(continenteDTO.getCode(), continenteDTO.getName()))));

        return this.paisRepository.save(newPais);
    }
}
