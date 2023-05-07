package pl.store.business;

import pl.store.domain.Producer;

import java.util.List;

public interface ProducerRepository {
    Producer create(Producer producer);
    List<Producer> findAll();
    void deleteAll();
}
