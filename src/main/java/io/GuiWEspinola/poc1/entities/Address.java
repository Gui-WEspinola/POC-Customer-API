package io.GuiWEspinola.poc1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Street name must not be empty")
    private String street;

    @NotEmpty
    private String addressNumber;

    @NotEmpty
    private String district;

    @NotEmpty
    @Column(length = 110)
    private String city;

    @NotEmpty
    @Column(length = 8)
    private String zipCode;

    @NotEmpty
    private String state;

    @JsonIgnore
    private Boolean mainAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
