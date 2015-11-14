package org.openpkw.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test spring configuration
 * @author Sebastian Pogorzelski
 */
@ComponentScan(basePackages = "org.openpkw",
        excludeFilters = {@ComponentScan.Filter(value = {Controller.class, RestController.class})})
public class TestAppConfig {
}
