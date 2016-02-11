package org.openpkw.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test spring configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@ComponentScan(basePackages = {"org.openpkw.services.qr","org.openpkw.services", "org.openpkw.rest","org.openpkw.validation"})
public class TestAppConfig {

}
