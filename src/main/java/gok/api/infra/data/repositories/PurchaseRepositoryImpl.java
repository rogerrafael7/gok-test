package gok.api.infra.data.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import gok.api.domain.models.purchase.PurchaseModel;
import gok.api.domain.repositories.PurchaseRepository;
import gok.api.infra.data.entities.PurchaseEntity;
import gok.api.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class PurchaseRepositoryImpl extends PurchaseRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PurchaseModel getPurchaseByIdOrFail(Long id) throws ServerException {
        var purchaseEntity = entityManager.find(PurchaseEntity.class, id);
        if (purchaseEntity == null) {
            throw new ServerException("Purchase not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        return purchaseEntity;
    }

    @Override
    public List<PurchaseModel> getPurchasesByCustomerId(Long customerId) {
        return entityManager.createQuery(
                "SELECT p FROM purchases p WHERE p.customerId = :customerId", 
                PurchaseModel.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    @Override
    public List<PurchaseModel> getPurchasesByProductId(Long productId) {
        return entityManager.createQuery(
                "SELECT p FROM purchases p WHERE p.productId = :productId", 
                PurchaseModel.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public PaginationResponse<PurchaseModel> getPurchases(PaginationRequest paginationRequest) {
        var query = entityManager.createQuery("SELECT p FROM purchases p", PurchaseModel.class);
        var page = paginationRequest.getPage();
        var limit = paginationRequest.getLimit();
        int offset = (page - 1) * limit;

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<PurchaseModel> purchases = query.getResultList();

        Long count = entityManager.createQuery("SELECT COUNT(p) FROM purchases p", Long.class).getSingleResult();

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
                purchases
        );
    }

    @Transactional
    @Override
    public PurchaseModel createPurchase(Long customerId, Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCustomerId(customerId);
        purchaseEntity.setProductId(productId);
        purchaseEntity.setQuantity(quantity);
        purchaseEntity.setUnitPrice(unitPrice);
        purchaseEntity.setTotalPrice(totalPrice);
        entityManager.persist(purchaseEntity);
        return purchaseEntity;
    }

    @Transactional
    @Override
    public void updatePurchase(Long id, Long customerId, Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        PurchaseEntity purchaseEntity = entityManager.find(PurchaseEntity.class, id);
        if (purchaseEntity == null) {
            throw new ServerException("Purchase not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        if (customerId != null) {
            purchaseEntity.setCustomerId(customerId);
        }
        if (productId != null) {
            purchaseEntity.setProductId(productId);
        }
        if (quantity != null) {
            purchaseEntity.setQuantity(quantity);
        }
        if (unitPrice != null) {
            purchaseEntity.setUnitPrice(unitPrice);
        }
        if (totalPrice != null) {
            purchaseEntity.setTotalPrice(totalPrice);
        }
        entityManager.persist(purchaseEntity);
    }

    @Transactional
    @Override
    public void deletePurchase(Long id) {
        PurchaseEntity purchaseEntity = entityManager.find(PurchaseEntity.class, id);
        if (purchaseEntity == null) {
            throw new ServerException("Purchase not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        entityManager.remove(purchaseEntity);
    }
} 