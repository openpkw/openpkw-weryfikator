package org.openpkw.repositories.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Tomasz Łabuz on 2015-07-17.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
								basePackages = {"org.openpkw.repositories"},
								entityManagerFactoryRef = "emf")
public class JpaConfiguration {
	
				@Autowired
    EntityManagerFactory emf;
 
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = 
            new JpaTransactionManager();
            tm.setEntityManagerFactory(emf);
        return tm;
    }
	/*
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(emf);
    return txManager;
  }
		*/
}
