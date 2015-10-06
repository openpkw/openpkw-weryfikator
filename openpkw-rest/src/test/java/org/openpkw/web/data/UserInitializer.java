package org.openpkw.web.data;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserType;
import org.openpkw.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Helper for creating users
 * @author  Sebastian Pogorzelski
 */
@Component
public class UserInitializer {

    @Inject
    private UserRepository userRepository;

    public void createUsers() {
        userRepository.save(createUser("admin@openpkw.pl", "$2a$10$Lig1ZxxfGt7izKzdjLSOjO7KVFArC5RpcJnxmchmARCP0fRpAoWBq", UserType.ADMINISTRATOR));//password = admin
        userRepository.save(createUser("admin1@openpkw.pl", "$2a$10$m653E75NHw9WdFe9br0hQuBEsEOmp/n9Fqy2mF63zMV0wYdPoxo56", UserType.ADMINISTRATOR));//password = admin1
        userRepository.save(createUser("vol@openpkw.pl", "$2a$10$LimK8fu73pv9Wua0VLk5S.nPFXRTSbeqUdbs.xokUWlJHhgJyVEyG", UserType.VOLUNTEER));//password = vol
        userRepository.save(createUser("vol1@openpkw.pl", "$2a$10$S6/5VLLWaOi0.gITzb6jCO6MPGxV90SAP2FXis174Hu6NpeTBirrK", UserType.VOLUNTEER));//password = vol1
    }

    private User createUser(String email, String password, UserType type) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(type);
        user.setIsActive(Boolean.TRUE);
        return user;
    }

}
