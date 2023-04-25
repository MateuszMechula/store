package pl.store.business;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.store.domain.*;
import pl.store.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ReloadDataServiceTest {

    private ReloadDataService reloadDataService;

    private CustomerService customerService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;
    private PurchaseService purchaseService;

    @Test
    void thatDataIsReloaded() {
        // given
        reloadDataService.reloadData();
        // when
        List<Customer> allCustomers = customerService.findAll();
        List<Producer> allProducers = producerService.findAll();
        List<Product> allProducts = productService.findAll();
        List<Purchase> allPurchase = purchaseService.findAll();
        List<Opinion> allOpinions = opinionService.findAll();
        // then
        Assertions.assertEquals(100, allCustomers.size());
        Assertions.assertEquals(20, allProducers.size());
        Assertions.assertEquals(50, allProducts.size());
        Assertions.assertEquals(300, allPurchase.size());
        Assertions.assertEquals(140, allOpinions.size());

    }

}