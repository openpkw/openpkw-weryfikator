package org.openpkw.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Test spring configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@ComponentScan(basePackages = {"org.openpkw.services.qr","org.openpkw.services", "org.openpkw.rest","org.openpkw.validation", "org.openpkw.web.utils"})
@PropertySource("classpath:openpkw.properties")
public class TestAppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
