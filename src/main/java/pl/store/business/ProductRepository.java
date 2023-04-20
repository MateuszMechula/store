package pl.store.business;

import pl.store.domain.Product;

public interface ProductRepository {
    Product create(Product product);
}
