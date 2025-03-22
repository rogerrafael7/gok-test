package gok.api.domain.repositories;

import gok.api.domain.models.purchase.PurchaseModel;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.math.BigDecimal;
import java.util.List;

public abstract class PurchaseRepository {
    public abstract PurchaseModel getPurchaseByIdOrFail(Long id) throws ServerException;
    public abstract List<PurchaseModel> getPurchasesByCustomerId(Long customerId);
    public abstract List<PurchaseModel> getPurchasesByProductId(Long productId);
    public abstract PaginationResponse<PurchaseModel> getPurchases(PaginationRequest paginationRequest);
    public abstract PurchaseModel createPurchase(Long customerId, Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice);
    public abstract void updatePurchase(Long id, Long customerId, Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice);
    public abstract void deletePurchase(Long id);
} 