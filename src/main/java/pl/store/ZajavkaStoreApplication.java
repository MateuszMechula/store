package pl.store;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.store.business.ReloadDataService;
import pl.store.infrastructure.configuration.ApplicationConfiguration;

public class ZajavkaStoreApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        ReloadDataService random = applicationContext.getBean(ReloadDataService.class);
         random.loadRandomData();


    }
}