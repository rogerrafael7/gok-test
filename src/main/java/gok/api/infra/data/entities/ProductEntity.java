package gok.api.infra.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import gok.api.domain.models.product.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "products")
@Getter
@Setter
public class ProductEntity extends ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Double price;

    private String description;

    private String type;
}
