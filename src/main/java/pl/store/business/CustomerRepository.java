package pl.store.business;

import pl.store.domain.Customer;

public interface CustomerRepository {
    Customer create(Customer customer);

    void deleteAll();
}
