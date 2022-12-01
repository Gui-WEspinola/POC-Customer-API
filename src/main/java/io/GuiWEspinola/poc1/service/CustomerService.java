package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerService extends JpaRepository<Customer, Long> {
}
