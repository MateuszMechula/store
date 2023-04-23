package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.store.domain.Opinion;

import java.util.List;

@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        return opinionRepository.create(opinion);
    }

    @Transactional
    public void removeAll() {
        opinionRepository.deleteAll();
    }

    @Transactional
    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }
    @Transactional
    public void removeAll(String email) {
        opinionRepository.remove(email);
    }
}
