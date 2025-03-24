package gok.api.application.services;

import gok.api.domain.models.TopCustomerModel;
import gok.api.domain.models.ProductModel;
import gok.api.domain.repositories.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<TopCustomerModel> getTopCustomers() {
        return customerRepository.getTopCustomers();
    }

    public List<ProductModel> getSuggestionProduct(Long customerId) {
        return customerRepository.getSuggestionProduct(customerId);
    }
}
