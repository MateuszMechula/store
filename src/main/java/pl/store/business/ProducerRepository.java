package pl.store.business;

import pl.store.domain.Producer;

import java.util.List;

public interface ProducerRepository {
    Producer create(Producer producer);
    void deleteAll();

    List<Producer> findAll();
}
