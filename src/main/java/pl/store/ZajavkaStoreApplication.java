package pl.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.store.business.ReloadDataService;
import pl.store.infrastructure.configuration.ApplicationConfiguration;

@Slf4j
public class ZajavkaStoreApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        ReloadDataService random = applicationContext.getBean(ReloadDataService.class);
         random.reloadData();
    }
}