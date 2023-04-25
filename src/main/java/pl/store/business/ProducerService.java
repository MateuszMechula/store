package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Producer;

import java.util.List;

@Service
@AllArgsConstructor
public class ProducerService {

    private final ProductService productService;
    private final ProducerRepository producerRepository;

    @Transactional
    public Producer create(Producer producer) {
        return producerRepository.create(producer);
    }

    @Transactional
    public void removeAll() {
        productService.removeAll();
        producerRepository.deleteAll();
    }

    public List<Producer> findAll() {
        return producerRepository.findAll();
    }
}