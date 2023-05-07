package pl.store.business;

import pl.store.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);
    List<Opinion> findAll();
    List<Opinion> findAll(String email);
    List<Opinion> findUnwantedOpinions();
    List<Opinion> findAllByProductCode(String productCode);
    public boolean findConsumersGivesUnwantedOpinions(String email);
    void deleteAll();
    void remove(String email);
    void removeUnwantedOpinions();
    void removeAllByProductCode(String productCode);
}
