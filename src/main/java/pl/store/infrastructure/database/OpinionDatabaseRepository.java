package pl.store.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.store.business.OpinionRepository;
import pl.store.domain.Opinion;
import pl.store.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class OpinionDatabaseRepository implements OpinionRepository {

    private static final String SELECT_ALL_OPINIONS = "SELECT * FROM OPINION";
    private final static String SELECT_UNWANTED_OPINIONS = "SELECT * FROM OPINION WHERE STARS < 4";
    private static final String SELECT_ALL_WHERE_PRODUCT_CODE = """
            SELECT * FROM OPINION
            WHERE PRODUCT_ID = (SELECT ID FROM PRODUCT WHERE PRODUCT_CODE = :product_code)
            """;
    private static final String SELECT_UNWANTED_OPINIONS_FOR_EMAIL = """
            SELECT * FROM OPINION
            WHERE STARS < 4
            AND CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)
            """;
    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL = """
            SELECT * FROM OPINION AS OPN
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = OPN.CUSTOMER_ID
                WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
            """;
    private final static String DELETE_ALL = "DELETE FROM OPINION WHERE 1=1";
    private static final String DELETE_UNWANTED_OPINIONS = "DELETE FROM OPINION WHERE STARS < 4";
    private static final String DELETE_ALL_WHERE_CUSTOMER_EMAIL =
            "DELETE FROM OPINION WHERE CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";
    private static final String DELETE_ALL_WHERE_PRODUCT_CODE = """
            DELETE FROM OPINION
            WHERE PRODUCT_ID IN (SELECT ID FROM PRODUCT WHERE PRODUCT_CODE = :product_code)
            """;
    private final DatabaseMapper databaseMapper;
    private final SimpleDriverDataSource simpleDriverDataSource;
    @Override
    public Opinion create(Opinion opinion) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(DatabaseConfiguration.OPINION_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.OPINION_TABLE_PKEY.toLowerCase());

        Map<String, ?> params = databaseMapper.map(opinion);
        Number opinionId = simpleJdbcInsert.executeAndReturnKey(params);
        return opinion.withId((long) opinionId.intValue());
    }
    @Override
    public List<Opinion> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_OPINIONS, DatabaseMapper::mapOpinion);
    }
    @Override
    public List<Opinion> findAll(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_WHERE_CUSTOMER_EMAIL,Map.of("email", email), DatabaseMapper::mapOpinion);
    }
    @Override
    public List<Opinion> findUnwantedOpinions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_UNWANTED_OPINIONS, DatabaseMapper::mapOpinion);

    }
    @Override
    public List<Opinion> findAllByProductCode(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate
                .query(SELECT_ALL_WHERE_PRODUCT_CODE, Map.of("product_code", productCode), DatabaseMapper::mapOpinion);
    }
    @Override
    public boolean findConsumersGivesUnwantedOpinions(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate
                .query(SELECT_UNWANTED_OPINIONS_FOR_EMAIL,Map.of("email", email), DatabaseMapper::mapOpinion)
                .size() > 0;
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
    public void removeUnwantedOpinions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_UNWANTED_OPINIONS);
    }
    @Override
    public void removeAllByProductCode(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate
                .update(DELETE_ALL_WHERE_PRODUCT_CODE, Map.of("product_code", productCode));
    }

}
