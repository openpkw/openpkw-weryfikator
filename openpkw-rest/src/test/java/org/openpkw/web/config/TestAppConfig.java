package org.openpkw.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * Test spring configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@ComponentScan(basePackages = {"org.openpkw.services.qr", "org.openpkw.services","org.openpkw.web.validation"})
public class TestAppConfig {

}
