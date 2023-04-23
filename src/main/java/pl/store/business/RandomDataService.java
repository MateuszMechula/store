package pl.store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.*;


@Slf4j
@Service
@AllArgsConstructor
public class RandomDataService {

    private final RandomDataPreparationService randomDataPreparationService;
    private final CustomerRepository customerRepository;
    private final ProducerRepository producerRepository;
    private final OpinionRepository opinionRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public void create() {
        Customer customer = customerRepository.create(randomDataPreparationService.createCustomer());
        Producer producer = producerRepository.create(randomDataPreparationService.createProducer());
        Product product = productRepository.create(randomDataPreparationService.createProduct(producer));
        Opinion opinion = opinionRepository.create(randomDataPreparationService.createOpinion(customer,product));
        Purchase purchase = purchaseRepository. create(randomDataPreparationService.createPurchase(customer,product));

        log.debug("Random customer created: [{}]", customer);
        log.debug("Random opinion created: [{}]", opinion);
        log.debug("Random producer created: [{}]", producer);
        log.debug("Random product created: [{}]", product);
        log.debug("Random purchase created: [{}]", purchase);
    }
}
