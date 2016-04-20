package org.openpkw.web.utils;

import org.springframework.core.env.AbstractEnvironment;

/**
 * @author Sebastian Pogorzelski
 */
public class SpringProfileHelper {

    public static boolean integrationTestsDisabled() {
        return System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME) == null ||
                !System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME).contains("integration-tests");
    }
}
