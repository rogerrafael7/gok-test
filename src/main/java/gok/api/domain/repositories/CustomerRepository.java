package gok.api.domain.repositories;

import gok.api.domain.models.TopCustomerModel;
import gok.api.domain.models.ProductModel;

import java.util.List;

public abstract class CustomerRepository {
    public abstract List<TopCustomerModel> getTopCustomers();
    public abstract List<ProductModel> getSuggestionProduct(Long customerId, Long categoryId);
}
