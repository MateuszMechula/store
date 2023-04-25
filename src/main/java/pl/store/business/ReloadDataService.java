package pl.store.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import pl.store.infrastructure.database.ReloadDataDatabaseRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class ReloadDataService {

    private final CustomerService customerService;
    private final ProducerService producerService;
    private final RandomDataService randomDataService;
    private final ReloadDataDatabaseRepository reloadDataDatabaseRepository;

    @Transactional
    public void loadRandomData() {
        customerService.removeAll();
        producerService.removeAll();
        for (int i = 0; i < 10; i++) {
            randomDataService.create();
        }

    }
    @Transactional
    public void reloadData() {
        customerService.removeAll();
        producerService.removeAll();

        try {
            final Path path = ResourceUtils.getFile("classpath:w15-project-sql-inserts.sql").toPath();
            Stream.of(Files.readString(path).split("INSERT"))
                    .filter(line -> !line.isBlank())
                    .map(line -> "INSERT" + line)
                    .toList()
                    .forEach(reloadDataDatabaseRepository::run);
        } catch (Exception e) {
            log.error("Unable to load SQL inserts", e);
        }

    }
}
