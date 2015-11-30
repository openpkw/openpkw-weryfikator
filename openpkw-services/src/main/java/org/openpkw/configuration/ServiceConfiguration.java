package org.openpkw.configuration;

import configuration.JpaRepoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Admin on 2015-11-23.
 */
@Configuration
@Import(JpaRepoConfiguration.class)
public class ServiceConfiguration {

}
