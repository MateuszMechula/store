package pl.store.business;

import pl.store.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product create(Product product);
    void deleteAll();

    List<Product> findAll();

    Optional<Product> find(String productCode);
}
