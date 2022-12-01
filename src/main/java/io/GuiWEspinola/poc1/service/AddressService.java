package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressService extends JpaRepository<Address, Long> {
}
