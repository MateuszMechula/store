package pl.store.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.store.business.integration.domain.SomeFixtures;
import pl.store.domain.Opinion;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpinionServiceTest {
    @InjectMocks
    private OpinionService opinionService;
    @Mock
    private PurchaseService purchaseService;
    @Mock
    private OpinionRepository opinionRepository;

    @Test
    @DisplayName("Polecenie 5 cz.1")
    void thatOpinionCanBeCreatedForProductThatCustomerActuallyBought() {
        // given
        final var customer = SomeFixtures.someCustomer();
        final var producer = SomeFixtures.someProducer();
        final var product = SomeFixtures.someProduct1(producer);
        final var purchase = SomeFixtures.somePurchase(customer, product);
        final var opinion = SomeFixtures.someOpinion(customer, product.withId(1L));

        when(purchaseService.findAll(customer.getEmail(), product.getProductCode()))
                .thenReturn(List.of(purchase.withId(1L)));
        when(opinionRepository.create(any(Opinion.class))).thenReturn(opinion.withId(10L));

        // when
        Opinion result = opinionService.create(opinion);

        // then
        verify(opinionRepository).create(any(Opinion.class));
        Assertions.assertEquals(opinion.withId(10L), result);
    }

    @Test
    @DisplayName("Polecenie 5 cz.2")
    void thatOpinionCanNotBeCreatedForProductThatCustomerDidNotBuy() {
        // given
        final var customer = SomeFixtures.someCustomer();
        final var producer = SomeFixtures.someProducer();
        final var product = SomeFixtures.someProduct1(producer);
        final var opinion = SomeFixtures.someOpinion(customer, product.withProductCode("testCode"));

        when(purchaseService.findAll(anyString(), anyString())).thenReturn(List.of());

        // when, then
        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> opinionService.create(opinion));
        Assertions.assertEquals(
                String.format("Product codes mismatch. Customer: [%s] wants to give opinion for product: [%s] that didnt purchase",
                        customer.getEmail(), opinion.getProduct().getProductCode()),
                exception.getMessage()
        );

        verify(opinionRepository, never()).create(any(Opinion.class));
    }
}