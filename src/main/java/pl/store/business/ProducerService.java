package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProducerService {

    private final ProductService productService;
    private final ProducerRepository producerRepository;

    @Transactional
    public void removeAll() {
        productService.removeAll();
        producerRepository.deleteAll();
    }
}