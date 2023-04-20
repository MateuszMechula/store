package pl.store.business;

import pl.store.domain.Customer;

public class CustomperRepositoryImpl implements CustomerRepository{
    @Override
    public Customer create(Customer customer) {
        return customer;
    }
}
