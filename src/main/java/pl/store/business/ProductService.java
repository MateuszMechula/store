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
    private final OpinionService opinionService;
    private final ProductRepository productRepository;
    @Transactional
    public Product create(Product product) {
        return productRepository.create(product);
    }
    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @Transactional
    public Product find(String productCode){
        return productRepository.find(productCode)
                .orElseThrow(() -> new RuntimeException("Product with productCode: [%s] is missing".formatted(productCode)));
    }
    @Transactional
    public void removeAll() {
        purchaseService.removeALl();
        productRepository.deleteAll();
    }
    @Transactional
    public void removeCompletely(String productCode) {
        opinionService.removeAllByProductCode(productCode);
        purchaseService.removeAllByProductCode(productCode);
        productRepository.remove(productCode);
    }
}
