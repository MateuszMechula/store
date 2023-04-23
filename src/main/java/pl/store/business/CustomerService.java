package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Customer;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class CustomerService {

    private final OpinionService opinionService;
    private final PurchaseService purchaseService;
    private final CustomerRepository customerRepository;


    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.create(customer);
    }

    @Transactional
    public void removeAll() {
        opinionService.removeAll();
        purchaseService.removeALl();
        customerRepository.deleteAll();
    }

    @Transactional
    public Customer find(String email) {
        return customerRepository.find(email)
                .orElseThrow(() -> new RuntimeException("Customer with email: [%s] is missing".formatted(email)));
    }

    @Transactional
    public void remove(String email) {
        Customer existingCustomer = find(email);

        opinionService.removeAll(email);

        purchaseService.removeALl(email);

        if (isOlderThan40(existingCustomer)) {
            throw new RuntimeException("Could not remove customer because he/she is older than 40");
        }

        customerRepository.remove(email);
    }

    private boolean isOlderThan40(Customer existingCustomer) {
        return LocalDate.now().getYear() - existingCustomer.getDateOfBirth().getYear() > 40;
    }
}
