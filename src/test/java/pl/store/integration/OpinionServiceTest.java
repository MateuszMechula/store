package pl.store.integration;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.store.business.OpinionService;
import pl.store.business.ReloadDataService;
import pl.store.domain.Opinion;
import pl.store.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OpinionServiceTest {

    private ReloadDataService reloadDataService;
    private OpinionService opinionService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(reloadDataService);
        Assertions.assertNotNull(opinionService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Polecenie 7")
    void thatUnwantedOpinionsAreRemoved() {
        // given
        Assertions.assertEquals(140, opinionService.findAll().size());
        List<Opinion> unWantedOpinions = opinionService.findUnwantedOpinions();
        // when
        opinionService.removeUnwantedOpinions();

        // then
        Assertions.assertEquals(140 - unWantedOpinions.size(), opinionService.findAll().size());
    }
}
