package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
