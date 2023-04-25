package pl.store.business;

import pl.store.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer create(Customer customer);

    void deleteAll();

    Optional<Customer> find(String email);

    List<Customer> findAll();

    void remove(String email);
}
