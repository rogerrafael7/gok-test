package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerModel {
    private Long id;
    private String name;
    private String taxId;
    private List<OrderModel> orders;
}