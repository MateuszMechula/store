package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.CustomerRepository;
import pl.store.domain.Customer;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDatabaseRepository implements CustomerRepository {

    private final static String DELETE_ALL = "DELETE FROM CUSTOMER WHERE 1=1";
    private final SimpleDriverDataSource simpleDriverDataSource;
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
}
