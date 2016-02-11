package org.openpkw.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test spring configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@ComponentScan(basePackages = {"org.openpkw.qr", "org.openpkw.services", "org.openpkw.web","org.openpkw.rest"})
public class TestAppConfig {

}
