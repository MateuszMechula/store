package pl.store.business;

import pl.store.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);
    void deleteAll();
    void remove(String email);

    List<Opinion> findAll();
    List<Opinion> findAll(String email);

    List<Opinion> findUnwantedOpinions();
    public boolean consumerGivesUnwantedOpinions(String email);

    void removeUnwantedOpinions();
}
