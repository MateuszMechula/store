package pl.store.business;

import pl.store.domain.Producer;

public interface ProducerRepository {
    Producer create(Producer producer);
}
