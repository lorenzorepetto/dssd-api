package com.dssd.grupo15.backend.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.dssd.grupo15.backend.controller.SociedadController;
import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.exception.FileStorageException;
import com.dssd.grupo15.backend.model.File;
import com.dssd.grupo15.backend.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");
    private final FileRepository fileRepository;

    @Autowired
    public FilesStorageServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public File save(MultipartFile file, SociedadAnonimaDTO sociedadAnonimaDTO) throws FileStorageException {
        try {
            String fileUrl = String.format("%s_%s_estatuto.%s",
                    sociedadAnonimaDTO.getNombre(),
                    sociedadAnonimaDTO.getFechaCreacion().toString().replace("-", ""),
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));

            Files.copy(file.getInputStream(), this.root.resolve(fileUrl));
            File newFile = new File();
            newFile.setName(file.getOriginalFilename());
            String url = MvcUriComponentsBuilder.fromController(SociedadController.class).build().toString()
                    .concat(root.toString())
                    .concat("/")
                    .concat(fileUrl);
            newFile.setUrl(url);

//            java.io.File tmpFile = new java.io.File("src/main/resources/targetFile.tmp");
//            try (OutputStream os = new FileOutputStream(tmpFile)) {
//                os.write(file.getBytes());
//            }
//            GDriveService.test(tmpFile);

            return this.fileRepository.save(newFile);
        } catch (Exception e) {
            throw new FileStorageException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .message("Could not store the file. Error: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException(StatusCodeDTO.Builder.aStatusCodeDTO()
                        .message("Could not read the file: " + filename)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
            }
        } catch (MalformedURLException | FileStorageException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() throws FileStorageException {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new FileStorageException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .message("Could not load the files!")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
    }
}
