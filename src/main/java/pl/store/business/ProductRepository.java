package pl.store.business;

import pl.store.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product create(Product product);
    Optional<Product> find(String productCode);
    List<Product> findAll();
    void deleteAll();
    void remove(String productCode);
}
