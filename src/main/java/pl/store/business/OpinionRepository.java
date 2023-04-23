package pl.store.business;

import pl.store.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);
    void deleteAll();

    void remove(String email);

    List<Opinion> findAll(String email);
}
