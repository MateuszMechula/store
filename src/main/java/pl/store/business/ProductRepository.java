package pl.store.business;

import pl.store.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product create(Product product);
    void deleteAll();

    List<Product> findAll();
}
