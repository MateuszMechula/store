package pl.store.business;

import pl.store.domain.Opinion;

public class OpinionRepositoryImpl implements OpinionRepository{
    @Override
    public Opinion create(Opinion opinion) {
        return opinion;
    }
}
