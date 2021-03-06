package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.exception.FileStorageException;
import com.dssd.grupo15.backend.model.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
    void init();

    File save(MultipartFile file, SociedadAnonimaDTO sociedadAnonimaDTO) throws FileStorageException;

    Resource load(String filename);

    void deleteAll();

    Stream<Path> loadAll() throws FileStorageException;
}
