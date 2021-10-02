package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.utils.SociedadMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SociedadController extends GenericController {

    @PostMapping("/sociedad")
    public Object createSociedad(@RequestParam("estatuto") MultipartFile file, @RequestParam("sociedad_anonima") String sociedadAnonimaString) throws JSONException {

        SociedadAnonimaDTO sociedadAnonimaDTO = SociedadMapper.mapFromJSONObject(new JSONObject(sociedadAnonimaString));

        return sociedadAnonimaDTO;
    }


}
