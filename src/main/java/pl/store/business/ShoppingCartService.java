package pl.store.business;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Customer;
import pl.store.domain.Product;
import pl.store.domain.Purchase;

import java.time.OffsetDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private final CustomerService customerService;
    private final ProductService productService;
    private final PurchaseService purchaseService;
    @Transactional
    public void makeAPurchase(String email, String productCode, int quantity) {
        Customer customer = customerService.find(email);
        Product product = productService.find(productCode);
        Purchase purchase = purchaseService.create(Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .dateTime(OffsetDateTime.now())
                .build());
        log.info("Customer: [{}] made a purchase for product: [{}], quantity: [{}]",
                email, product, quantity);
        log.debug("Customer: [{}] made a purchase for product: [{}], quantity: [{}], purchase: [{}]",
                email, product, quantity, purchase);
    }
}
