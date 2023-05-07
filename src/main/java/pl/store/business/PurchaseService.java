package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Purchase;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    @Transactional
    public Purchase create(Purchase purchase) {
        return purchaseRepository.create(purchase);
    }
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }
    public List<Purchase> findAll(String email) {
        return purchaseRepository.findAll(email);
    }
    public List<Purchase> findAll(String email, String productCode) {
        return purchaseRepository.findAll(email,productCode);
    }
    public List<Purchase> findAllByProductCode(String productCode) {
        return purchaseRepository.findAllByProductCode(productCode);
    }
    @Transactional
    public void removeALl() {
        purchaseRepository.deleteAll();
    }
    @Transactional
    public void removeALl(String email) {
        purchaseRepository.remove(email);
    }
    public void removeAllByProductCode(String productCode) {
        purchaseRepository.removeAllByProductCode(productCode);
    }
}
