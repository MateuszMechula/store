package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.PurchaseRepository;
import pl.store.domain.Purchase;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;


@Slf4j
@Repository
@AllArgsConstructor
public class PurchaseDatabaseRepository implements PurchaseRepository {

    private static final String SELECT_ALL_PURCHASE = "SELECT * FROM PURCHASE";
    private static final String SELECT_ALL_WHERE_PRODUCT_CODE = """
            SELECT * FROM PURCHASE
            WHERE PRODUCT_ID = (SELECT ID FROM PRODUCT WHERE PRODUCT_CODE = :product_code)
            """;
    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL = """
            SELECT * FROM PURCHASE AS PUR
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = PUR.CUSTOMER_ID
                WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
            """;
    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL_AND_PRODUCT_CODE = """
            SELECT * FROM PURCHASE AS PUR
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = PUR.CUSTOMER_ID
                INNER JOIN PRODUCT AS PROD ON PROD.ID = PUR.PRODUCT_ID
                WHERE CUS.EMAIL = :email
                AND PROD.PRODUCT_CODE = :product_code
            """;
    private final static String DELETE_ALL = "DELETE FROM PURCHASE WHERE 1=1";
    private static final String DELETE_ALL_WHERE_PRODUCT_CODE = """
            DELETE FROM PURCHASE
            WHERE PRODUCT_ID IN (SELECT ID FROM PRODUCT WHERE PRODUCT_CODE = :product_code)
            """;
    private static final String DELETE_ALL_WHERE_CUSTOMER_EMAIL =
            "DELETE FROM PURCHASE WHERE CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";


    private final SimpleDriverDataSource simpleDriverDataSource;
    private final DatabaseMapper databaseMapper;
    @Override
    public Purchase create(Purchase purchase) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.PURCHASE_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PURCHASE_TABLE_PKEY.toLowerCase());

        Map<String, ?> params = databaseMapper.map(purchase);

        Number purchaseId = jdbcInsert.executeAndReturnKey(params);
        return purchase.withId((long) purchaseId.intValue());
    }
    @Override
    public List<Purchase> findAll() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_PURCHASE, DatabaseMapper::mapPurchase);
    }

    @Override
    public List<Purchase> findAll(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email), DatabaseMapper::mapPurchase);
    }

    @Override
    public List<Purchase> findAll(String email, String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(
                SELECT_ALL_WHERE_CUSTOMER_EMAIL_AND_PRODUCT_CODE,
                Map.of(
                        "email", email,
                        "product_code", productCode
                ),
                DatabaseMapper::mapPurchase
        );
    }
    @Override
    public List<Purchase> findAllByProductCode(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate
                .query(SELECT_ALL_WHERE_PRODUCT_CODE, Map.of("product_code", productCode), DatabaseMapper::mapPurchase);
    }

    @Override
    public void deleteAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }

    @Override
    public void remove(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email));
    }

    @Override
    public void removeAllByProductCode(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_ALL_WHERE_PRODUCT_CODE, Map.of("product_code", productCode));
    }
}
