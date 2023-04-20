package pl.store.business;

import pl.store.domain.Producer;

public class PrudcerRepositoryImpl implements ProducerRepository{
    @Override
    public Producer create(Producer producer) {
        return producer;
    }
}
