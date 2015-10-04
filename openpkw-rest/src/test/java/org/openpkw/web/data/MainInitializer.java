package org.openpkw.web.data;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Sebastian on 22.09.2015.
 */
@Component
public class MainInitializer implements InitializingBean {

    @Inject
    private UserInitializer userInitializer;

    @Override
    public void afterPropertiesSet() throws Exception {
        userInitializer.createUsers();
    }
}
