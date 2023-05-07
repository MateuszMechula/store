package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.ProducerRepository;
import pl.store.domain.Producer;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ProducerDatabaseRepository implements ProducerRepository {

    private static final String SELECT_ALL_PRODUCER = "SELECT * FROM PRODUCER";
    private final static String DELETE_ALL = "DELETE FROM PRODUCER WHERE 1=1";
    private final SimpleDriverDataSource simpleDriverDataSource;
    @Override
    public Producer create(Producer producer) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.PRODUCER_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PRODUCER_TABLE_PKEY.toLowerCase());

        final Number producerId = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(producer));

        return producer.withId((long) producerId.intValue());
    }
    @Override
    public List<Producer> findAll() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_PRODUCER, DatabaseMapper::mapProducer);
    }
    @Override
    public void deleteAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }
}
