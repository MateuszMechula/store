package pl.store.business;

import pl.store.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);
    List<Purchase> findAll();
    List<Purchase> findAll(String email);
    List<Purchase> findAll(String email, String productCode);
    List<Purchase> findAllByProductCode(String productCode);
    void deleteAll();
    void remove(String email);
    void removeAllByProductCode(String productCode);
}
