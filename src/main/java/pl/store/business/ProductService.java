package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Product;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final PurchaseService purchaseService;
    private final ProductRepository productRepository;


    @Transactional
    public Product create(Product product) {
        return productRepository.create(product);
    }

    @Transactional
    public void removeAll() {
        purchaseService.removeALl();
        productRepository.deleteAll();
    }

    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
