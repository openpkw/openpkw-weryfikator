package org.openpkw.web.configuration;

import org.openpkw.configuration.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tomasz Łabuz on 2015-07-17.
 */
@ComponentScan(basePackages = "org.openpkw", excludeFilters = {@ComponentScan.Filter(value = {Controller.class, RestController.class})})
@ImportResource(value = {"classpath*:/META-INF/spring/openpkw-*.xml"})
@Import(ServiceConfiguration.class)
public class AppConfig {	

}