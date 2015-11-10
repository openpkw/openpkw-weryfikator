package org.openpkw.utils.csv;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseInitializer {
	private EntityManagerFactory factory = null;
	private EntityManager em = null;
	private final String PERSISTENCE_UNIT_NAME = "openpkw";
	private final static Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

	private void setupEntityManager() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}

	public static void main(String[] argv) {
		try {
			DatabaseInitializer databaseInitializer = new DatabaseInitializer();
			databaseInitializer.setupEntityManager();
		} catch (Exception ex) {
			log.error("main()", ex);
		}
	}
	
	
}
