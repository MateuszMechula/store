package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.ProductRepository;
import pl.store.domain.Product;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDatabaseRepository implements ProductRepository {

    private final static String DELETE_ALL = "DELETE FROM PRODUCT WHERE 1=1";
    private static final String SELECT_ALL_PRODUCT = "SELECT * FROM PRODUCT";
    private final SimpleDriverDataSource simpleDriverDataSource;

    private final DatabaseMapper databaseMapper;
    @Override
    public Product create(Product product) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.PRODUCT_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PRODUCT_TABLE_PKEY.toLowerCase());

        final Map<String, ?> params = databaseMapper.map(product);

        final Number productId = simpleJdbcInsert.executeAndReturnKey(params);
        return product.withId((long) productId.intValue());
    }
    @Override
    public void deleteAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }

    @Override
    public List<Product> findAll() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_PRODUCT, DatabaseMapper::mapProduct);
    }
}
