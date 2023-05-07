package pl.store.business;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.store.domain.*;
import pl.store.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ShoppingCartServiceTest {
    private final CustomerService customerService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final ShoppingCartService shoppingCartService;
    private final ReloadDataService reloadDataService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(reloadDataService);
        Assertions.assertNotNull(shoppingCartService);
        Assertions.assertNotNull(purchaseService);
        Assertions.assertNotNull(customerService);
        Assertions.assertNotNull(producerService);
        Assertions.assertNotNull(productService);
        reloadDataService.reloadData();
    }


    @Test
    @DisplayName("Polecenie 9")
    void thatProductCanBeBoughtByCustomer() {
        // given
        final Customer customer = customerService.create(SomeFixtures.someCustomer());
        final Producer producer = producerService.create(SomeFixtures.someProducer());
        final Product product1 = productService.create(SomeFixtures.someProduct1(producer));
        productService.create(SomeFixtures.someProduct2(producer));
        final List<Purchase> before = purchaseService.findAll(customer.getEmail(), product1.getProductCode());

        // when
        shoppingCartService.makeAPurchase(customer.getEmail(), product1.getProductCode(), 10);
        // then
        final var after = purchaseService.findAll(customer.getEmail(), product1.getProductCode());
        Assertions.assertEquals(before.size() + 1, after.size());

    }

}