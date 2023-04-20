package pl.store.business;

import pl.store.domain.Purchase;

public class PurchaseRepositoryImpl implements PurchaseRepository{
    @Override
    public Purchase create(Purchase purchase) {
        return purchase;
    }
}
