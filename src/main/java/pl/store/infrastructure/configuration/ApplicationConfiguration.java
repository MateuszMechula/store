package pl.store.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.store.infrastructure.database.DatabaseConfiguration;

@Configuration
@Import({DatabaseConfiguration.class})
@ComponentScan(basePackages = "pl.store")
public class ApplicationConfiguration {

}
