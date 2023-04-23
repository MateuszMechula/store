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

    @Transactional
    public void removeALl() {
        purchaseRepository.deleteAll();
    }

    public List<Purchase> findAll(String email) {
        return purchaseRepository.findAll(email);
    }

    @Transactional
    public void removeALl(String email) {
        purchaseRepository.remove(email);
    }
}
