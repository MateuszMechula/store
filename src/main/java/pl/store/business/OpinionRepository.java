package pl.store.business;

import pl.store.domain.Opinion;

public interface OpinionRepository {
    Opinion create(Opinion opinion);
    void deleteAll();
}
