package pl.store.business;

import pl.store.domain.Purchase;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);
    void deleteAll();
}
