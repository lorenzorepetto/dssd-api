package com.dssd.grupo15.backend.repository;

import com.dssd.grupo15.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
