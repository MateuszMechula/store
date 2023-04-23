package pl.store.business;

import pl.store.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);
    void deleteAll();

    void remove(String email);

    List<Purchase> findAll(String email);
}
