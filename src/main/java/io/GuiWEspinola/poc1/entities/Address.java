package io.GuiWEspinola.poc1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    private Long id;

    private String street;

    private Integer number;

    private String district;

    private String city;

    private String cep;

    private String state;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
