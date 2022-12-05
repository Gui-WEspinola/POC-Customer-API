package io.GuiWEspinola.poc1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.GuiWEspinola.poc1.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_name")
    private String name;

    @Column
    private String email;

    @Column
    private PersonType documentType;

    @Column
    private String documentNumber;

    @Column
    private Integer mobileNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Address> address = new ArrayList<>();
}
