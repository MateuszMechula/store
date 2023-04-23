package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.PurchaseRepository;
import pl.store.domain.Purchase;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class PurchaseDatabaseRepository implements PurchaseRepository {

    private final static String DELETE_ALL = "DELETE FROM PURCHASE WHERE 1=1";
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
    public void deleteAll() {
        new JdbcTemplate(simpleDriverDataSource).update(DELETE_ALL);
    }
}
