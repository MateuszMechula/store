package pl.store.business.integration.domain;

import pl.store.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SomeFixtures {

    public static Customer someCustomer() {
        return Customer.builder()
                .user_name("zajavkowicz")
                .email("jankowalski@gmail.com")
                .name("Jan")
                .surname("Kowalski")
                .dateOfBirth(LocalDate.of(1991,10,2))
                .telephoneNumber("+42421424124")
                .build();
    }

    public static Producer someProducer() {
        return Producer.builder()
                .producer_name("Zbigniew")
                .address("some Address")
                .build();
    }

    public static Product someProduct1(Producer producer) {
        return Product.builder()
                .productCode("2312312312")
                .productName("najelpszy")
                .productPrice(BigDecimal.valueOf(2323.16))
                .adultsOnly(false)
                .description("description")
                .producer(producer)
                .build();
    }

    public static Product someProduct2(Producer producer) {
        return Product.builder()
                .productCode("3424234")
                .productName("najgorszy")
                .productPrice(BigDecimal.valueOf(333))
                .adultsOnly(false)
                .description("description")
                .producer(producer)
                .build();
    }

    public static Purchase somePurchase(Customer customer, Product product){
        return Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(5)
                .dateTime(OffsetDateTime.of(2020, 1,1,12,9,10,1, ZoneOffset.ofHours(4)))
                .build();
    }

    public static Opinion someOpinion(Customer customer, Product product) {
        return Opinion.builder()
                .customer(customer)
                .product(product)
                .stars((byte) 4)
                .comment("My comment")
                .dateTime(OffsetDateTime.of(2020, 1,1,12,9,10,1, ZoneOffset.ofHours(4)))
                .build();
    }
}
