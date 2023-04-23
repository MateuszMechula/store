package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final PurchaseService purchaseService;
    private final ProductRepository productRepository;


    @Transactional
    public void removeAll() {
        purchaseService.removeALl();
        productRepository.deleteAll();
    }
}
