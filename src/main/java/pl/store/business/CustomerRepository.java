package pl.store.business;

import pl.store.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer create(Customer customer);

    void deleteAll();

    Optional<Customer> find(String email);

    void remove(String email);
}
