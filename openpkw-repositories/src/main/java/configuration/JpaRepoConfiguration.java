package configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Lukasz Franczuk.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = {"org.openpkw.repositories"}
)
@Import(JpaConfiguration.class)
public class JpaRepoConfiguration {
}
