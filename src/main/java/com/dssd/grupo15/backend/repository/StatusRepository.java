package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Integer countByStatus(String status);
}
