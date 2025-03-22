package gok.api.domain.repositories;

import gok.api.domain.models.customer.CustomerModel;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;

public abstract class CustomerRepository {
    public abstract CustomerModel getCustomerByIdOrFail(Long id) throws ServerException;
    public abstract PaginationResponse<CustomerModel> getCustomers(PaginationRequest paginationRequest);
    public abstract CustomerModel createCustomer(String name, String taxId);
    public abstract void updateCustomer(Long id, String name, String taxId);
    public abstract void deleteCustomer(Long id);
} 