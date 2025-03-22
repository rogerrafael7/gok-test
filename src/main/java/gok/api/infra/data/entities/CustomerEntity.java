package gok.api.infra.data.entities;

import jakarta.persistence.*;
import gok.api.domain.models.customer.CustomerModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "customers")
@Getter
@Setter
public class CustomerEntity extends CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "tax_id")
    private String taxId;

}