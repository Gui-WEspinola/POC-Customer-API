package io.GuiWEspinola.poc1.entities;

import io.GuiWEspinola.poc1.enums.PersonType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String name;

    @Column
    private String email;

    @Column(name = "person_type")
    private PersonType personType;

    @Column
    private String documentNumber;

    @Column
    private Integer mobileNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column
    private List<Address> address = new ArrayList<>();
}
