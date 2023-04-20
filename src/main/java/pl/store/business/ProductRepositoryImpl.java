package pl.store.business;

import pl.store.domain.Product;

public class ProductRepositoryImpl implements ProductRepository{
    @Override
    public Product create(Product product) {
        return product;
    }
}
