package org.openpkw.services.test;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserType;
import org.openpkw.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lukasz Franczuk on 2015-11-19.
 */
@Service
public class TestService {

    @Autowired
    private UserRepository userRepository;

    public void addUser() {
        User user = new User();
        user.setEmail("luk.franczu@gmail.com");
        user.setFirstName("Lukasz");
        user.setLastName("Fran");
        user.setIsActive(true);
        user.setToken("fsadsdfadsf");
        user.setUserType(UserType.ADMINISTRATOR);
        userRepository.saveAndFlush(user);
    }
}
