package io.GuiWEspinola.poc1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private Integer addressNumber;

    private String district;

    private String city;

    private String zipCode;

    private String state;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
