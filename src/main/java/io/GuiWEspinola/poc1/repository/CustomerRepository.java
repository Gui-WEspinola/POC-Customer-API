package io.GuiWEspinola.poc1.repository;

import io.GuiWEspinola.poc1.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

    boolean existsByDocumentNumber(String document);

    Page<Customer> findByNameLikeIgnoreCase(String name, Pageable pageable);

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
