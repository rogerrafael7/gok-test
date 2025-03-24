package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopCustomerModel {
    private Long id;
    private String name;
    private Long totalDeCompras;
    private Double totalGasto;
}
