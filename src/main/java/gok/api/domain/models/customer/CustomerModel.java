package gok.api.domain.models.customer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerModel {
    private Long id;
    private String name;
    private String taxId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 