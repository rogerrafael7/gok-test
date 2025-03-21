package gok.api.infra.shared.types;

public interface PaginationRequest {
    int getPage();
    int getLimit();
    void setPage(int page);
    void setLimit(int limit);
}
