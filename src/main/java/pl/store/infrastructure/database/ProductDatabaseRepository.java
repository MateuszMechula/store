package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.ProductRepository;
import pl.store.domain.Product;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDatabaseRepository implements ProductRepository {

    private static final String SELECT_ALL_PRODUCT = "SELECT * FROM PRODUCT";
    private static final String SELECT_WHERE_PRODUCT_CODE = "SELECT * FROM PRODUCT WHERE PRODUCT_CODE = :product_code";
    private final static String DELETE_ALL = "DELETE FROM PRODUCT WHERE 1=1";
    private static final String DELETE_WHERE_PRODUCT_CODE = "DELETE FROM PRODUCT WHERE PRODUCT_CODE = :product_code";
    private final DatabaseMapper databaseMapper;
    private final SimpleDriverDataSource simpleDriverDataSource;
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
    public Optional<Product> find(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);

        final Map<String, String> params = Map.of("product_code", productCode);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_WHERE_PRODUCT_CODE,
                    params, DatabaseMapper::mapProduct));
        } catch (Exception e) {
            log.warn("Trying to find non-existing product: [{}]", productCode);
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_PRODUCT, DatabaseMapper::mapProduct);
    }

    @Override
    public void remove(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_WHERE_PRODUCT_CODE, Map.of("product_code", productCode));
    }

    @Override
    public void deleteAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }
}
