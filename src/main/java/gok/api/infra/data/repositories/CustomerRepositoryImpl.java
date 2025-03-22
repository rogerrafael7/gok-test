package gok.api.infra.data.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import gok.api.domain.models.customer.CustomerModel;
import gok.api.domain.repositories.CustomerRepository;
import gok.api.infra.data.entities.CustomerEntity;
import gok.api.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;

@ApplicationScoped
public class CustomerRepositoryImpl extends CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public CustomerModel getCustomerByIdOrFail(Long id) throws ServerException {
        var customerEntity = entityManager.find(CustomerEntity.class, id);
        if (customerEntity == null) {
            throw new ServerException("Customer not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        return customerEntity;
    }

    @Override
    public PaginationResponse<CustomerModel> getCustomers(PaginationRequest paginationRequest) {
        var query = entityManager.createQuery("SELECT c FROM customers c", CustomerModel.class);
        var page = paginationRequest.getPage();
        var limit = paginationRequest.getLimit();
        int offset = (page - 1) * limit;

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<CustomerModel> customers = query.getResultList();

        Long count = entityManager.createQuery("SELECT COUNT(c) FROM customers c", Long.class).getSingleResult();

        var totalItems = count.intValue();
        var totalPages = (int) Math.ceil((double) totalItems / limit);
        var hasNextPage = page < totalPages;
        var hasPreviousPage = page > 1;

        return new PaginationResponse<>(
                page,
                limit,
                totalItems,
                totalPages,
                hasNextPage,
                hasPreviousPage,
                customers
        );
    }

    @Transactional
    @Override
    public CustomerModel createCustomer(String name, String taxId) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(name);
        customerEntity.setTaxId(taxId);
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    @Transactional
    @Override
    public void updateCustomer(Long id, String name, String taxId) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, id);
        if (customerEntity == null) {
            throw new ServerException("Customer not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        if (name != null) {
            customerEntity.setName(name);
        }
        if (taxId != null) {
            customerEntity.setTaxId(taxId);
        }
        entityManager.persist(customerEntity);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, id);
        if (customerEntity == null) {
            throw new ServerException("Customer not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        entityManager.remove(customerEntity);
    }
} 