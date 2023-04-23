package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Transactional
    public void removeALl() {
        purchaseRepository.deleteAll();
    }
}
