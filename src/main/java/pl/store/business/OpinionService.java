package pl.store.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;

    public void removeAll() {
        opinionRepository.deleteAll();
    }
}
