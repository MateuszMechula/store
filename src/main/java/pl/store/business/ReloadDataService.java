package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReloadDataService {

    private final CustomerService customerService;
    private final ProducerService producerService;
    private final RandomDataService randomDataService;

    @Transactional
    public void loadRandomData() {
        customerService.removeAll();
        producerService.removeAll();
        for (int i = 0; i < 10; i++) {
            randomDataService.create();
        }

    }
}
