package pl.store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Opinion;
import pl.store.domain.Purchase;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final PurchaseService purchaseService;

    @Transactional
    public Opinion create(Opinion opinion) {
        final String productCode = opinion.getProduct().getProductCode();
        final String email = opinion.getCustomer().getEmail();
        final List<Purchase> purchaseList = purchaseService.findAll(email, productCode);
        log.debug("Customer: [{}] made: [{}] purchase for product: [{}]", email,purchaseList.size(),productCode);

        if (purchaseList.isEmpty()) {
            throw new RuntimeException(
                    "Product codes mismatch. Customer: [%s] wants to give opinion for product: [%s] that didnt purchase".formatted(
                    email, productCode));
        }
        return opinionRepository.create(opinion);
    }

    @Transactional
    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }
    @Transactional
    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }
    public List<Opinion> findAllByProductCode(String productCode) {
        return opinionRepository.findAllByProductCode(productCode);
    }
    @Transactional
    public List<Opinion> findUnwantedOpinions() {
        return opinionRepository.findUnwantedOpinions();
    }
    @Transactional
    public boolean findCustomersGivesUnwantedOpinions(String email) {
        return opinionRepository.findConsumersGivesUnwantedOpinions(email);
    }
    @Transactional
    public void removeAll() {
        opinionRepository.deleteAll();
    }
    @Transactional
    public void removeAll(String email) {
        opinionRepository.remove(email);
    }
    @Transactional
    public void removeUnwantedOpinions() {
        opinionRepository.removeUnwantedOpinions();
    }
    public void removeAllByProductCode(String productCode) {
        opinionRepository.removeAllByProductCode(productCode);
    }
}
