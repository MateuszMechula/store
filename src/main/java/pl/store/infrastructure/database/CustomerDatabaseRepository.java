package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.CustomerRepository;
import pl.store.domain.Customer;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDatabaseRepository implements CustomerRepository {

    private final static String DELETE_ALL = "DELETE FROM CUSTOMER WHERE 1=1";
    private final static String SELECT_ONE_WHERE_EMAIL = "SELECT * FROM CUSTOMER WHERE EMAIL = :email";
    private static final String DELETE_WHERE_CUSTOMER_EMAIL = "DELETE FROM CUSTOMER WHERE EMAIL = :email";
    private final SimpleDriverDataSource simpleDriverDataSource;
    private final DatabaseMapper databaseMapper;
    @Override
    public Customer create(Customer customer) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.CUSTOMER_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.CUSTOMER_TABLE_PKEY.toLowerCase());

        Number customerId = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customer));
        return customer.withId((long) customerId.intValue());
    }

    @Override
    public void deleteAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }

    @Override
    public Optional<Customer> find(String email) {
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);

        Map<String, Object> params = Map.of("email", email);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ONE_WHERE_EMAIL, params, databaseMapper::mapCustomer));
        } catch (Exception e) {
            log.warn("Trying to find non-existing customer: [{}]", email);
            return Optional.empty();
        }
    }

    @Override
    public void remove(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_WHERE_CUSTOMER_EMAIL, Map.of("email", email));
    }
}
