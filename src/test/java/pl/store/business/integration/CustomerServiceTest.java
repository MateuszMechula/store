package pl.store.business.integration;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.store.business.*;
import pl.store.business.integration.domain.SomeFixtures;
import pl.store.domain.*;
import pl.store.infrastructure.configuration.ApplicationConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerServiceTest {

    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(customerService);
        assertNotNull(purchaseService);
        assertNotNull(opinionService);
        assertNotNull(producerService);
        assertNotNull(productService);
        reloadDataService.loadRandomData();
    }

    @Test
    @DisplayName("Polecenie 4 cz.1")
    void thatCustomerRemoveCorrectly() {
        // given
        final Customer customer = customerService.create(SomeFixtures.someCustomer());
        final Producer producer = producerService.create(SomeFixtures.someProducer());

        final Product product1 = productService.create(SomeFixtures.someProduct1(producer));
        final Product product2 = productService.create(SomeFixtures.someProduct2(producer));

        purchaseService.create(SomeFixtures.somePurchase(customer, product1).withQuantity(10));
        purchaseService.create(SomeFixtures.somePurchase(customer, product2).withQuantity(5));
        opinionService.create(SomeFixtures.someOpinion(customer, product1));

        assertEquals(customer, customerService.find(customer.getEmail()));

        // when
        customerService.remove(customer.getEmail());

        // then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.find(customer.getEmail()));
        assertEquals("Customer with email: [%s] is missing".formatted(customer.getEmail()), exception.getMessage());

        assertTrue(purchaseService.findAll(customer.getEmail()).isEmpty());
        assertTrue(opinionService.findAll(customer.getEmail()).isEmpty());



    }
    @Test
    @DisplayName("Polecenie 4 cz.2")
    void thatPurchaseIsNotRemovedWhenCustomerRemovingFails() {

    }






}