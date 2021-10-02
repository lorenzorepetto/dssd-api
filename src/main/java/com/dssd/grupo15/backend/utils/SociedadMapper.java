package com.dssd.grupo15.backend.utils;

import com.dssd.grupo15.backend.dto.rest.request.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SociedadMapper {

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    public static SociedadAnonimaDTO mapFromJSONObject(JSONObject sociedadAnonima) throws JSONException {
        SociedadAnonimaDTO sociedadAnonimaDTO = new SociedadAnonimaDTO();

        sociedadAnonimaDTO.setNombre(sociedadAnonima.getString("nombre"));
        sociedadAnonimaDTO.setFechaCreacion(LocalDateTime.parse(sociedadAnonima.getString("fechaCreacion"), inputFormatter).toLocalDate());
        sociedadAnonimaDTO.setDomicilioLegal(sociedadAnonima.getString("domicilioLegal"));
        sociedadAnonimaDTO.setDomicilioReal(sociedadAnonima.getString("domicilioReal"));
        sociedadAnonimaDTO.setEmail(sociedadAnonima.getString("correoElectronico"));

        // Apoderado y socios
        JSONObject apoderado = sociedadAnonima.getJSONObject("apoderado");
        sociedadAnonimaDTO.setApoderado(getSocioDTO(apoderado));
        sociedadAnonimaDTO.setSocios(getSociosDTO(sociedadAnonima.getJSONArray("socios")));

        // Paises
        sociedadAnonimaDTO.setPaises(getPaisesDTO(sociedadAnonima.getJSONArray("paises")));

        return sociedadAnonimaDTO;
    }

    private static SocioDTO getSocioDTO(JSONObject apoderado) throws JSONException {
        return new SocioDTO(apoderado.getString("nombre"),
                apoderado.getString("apellido"),
                BigDecimal.valueOf(apoderado.getDouble("aportes")));
    }

    private static PaisDTO getPaisDTO(JSONObject pais) throws JSONException {
        return PaisDTO.Builder.aPaisDTO()
                .code(pais.getString("code"))
                .name(pais.getString("name"))
                .continent(getContinenteDTO(pais.getJSONObject("continent")))
                .languages(getLenguajesDTO(pais.getJSONArray("languages")))
                .states(getEstadosDTO(pais.getJSONArray("states")))
                .build();
    }

    private static ContinenteDTO getContinenteDTO(JSONObject continent) throws JSONException {
        // return new ContinenteDTO(continent.getString("code"), continent.getString("name"));
        return new ContinenteDTO(continent.getString("code"), null); //TODO: remover
    }

    private static List<SocioDTO> getSociosDTO(JSONArray socios) throws JSONException {
        List<SocioDTO> sociosDTO = new ArrayList<>();
        for (int i = 0; i < socios.length(); i++) {
            sociosDTO.add(getSocioDTO(socios.getJSONObject(i)));
        }
        return sociosDTO;
    }

    private static List<PaisDTO> getPaisesDTO(JSONArray paises) throws JSONException {
        List<PaisDTO> paisesDTO = new ArrayList<>();
        for (int i = 0; i < paises.length(); i++) {
            paisesDTO.add(getPaisDTO(paises.getJSONObject(i)));
        }
        return paisesDTO;
    }

    private static List<EstadoDTO> getEstadosDTO(JSONArray estados) throws JSONException {
        List<EstadoDTO> estadosDTO = new ArrayList<>();
        for (int i = 0; i < estados.length(); i++) {
            estadosDTO.add(getEstadoDTO(estados.getJSONObject(i)));
        }
        return estadosDTO;
    }

    private static EstadoDTO getEstadoDTO(JSONObject estado) throws JSONException {
        return new EstadoDTO(estado.getString("code"), estado.getString("name"));
    }

    private static List<LenguajeDTO> getLenguajesDTO(JSONArray languages) throws JSONException {
        List<LenguajeDTO> lenguajesDTO = new ArrayList<>();
        for (int i = 0; i < languages.length(); i++) {
            lenguajesDTO.add(getLenguajeDTO(languages.getJSONObject(i)));
        }
        return lenguajesDTO;
    }

    private static LenguajeDTO getLenguajeDTO(JSONObject language) throws JSONException {
        return new LenguajeDTO(language.getString("code"), language.getString("name"));
    }
}
