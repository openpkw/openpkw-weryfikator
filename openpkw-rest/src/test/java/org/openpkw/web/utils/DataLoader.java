package org.openpkw.web.utils;

import org.openpkw.model.entity.User;
import org.openpkw.repositories.UserRepository;
import org.openpkw.services.init.InitService;
import org.openpkw.validation.RestClientException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Sebastian Pogorzelski
 */
@Component
public class DataLoader {

    public static final String ADMIN_USER = "admin@openpkw.pl";
    public static final String ADMIN_PASSWORD = "admin";

    public static final String PRIVATE_KEY = "MIIBCQIBADAQBgcqhkjOPQIBBgUrgQQAJwSB8TCB7gIBAQRIAie7ReLT9TUheJy5S+h77VoMl1SIOFRkOmrTzrAWcXHeo4q5JhifwHAELPlO9wfiqfKvSQHszINFkJCN/8qBHpUHxPvN4AUAoAcGBSuBBAAnoYGVA4GSAAQESuzfne0O40uBLgBHwJ5oBMSZusvPX0XfkU8KoJ6PywEQSNjNSQ4z2z6Tqra32TMKh8RR1bGH/eWVIm759aoZE4xBZ1sI7+4BQMdC5/ErCUkCxlRPkNYoWTmCMGkHeUWI2vhJwgChdMVFsEYE7W3ZRbFkT9EvULQwnRBZLOlGTlIweyZ8Pn5bK1SUmywaflc=";
    private static final String PUBLIC_KEY = "MIGnMBAGByqGSM49AgEGBSuBBAAnA4GSAAQESuzfne0O40uBLgBHwJ5oBMSZusvPX0XfkU8KoJ6PywEQSNjNSQ4z2z6Tqra32TMKh8RR1bGH/eWVIm759aoZE4xBZ1sI7+4BQMdC5/ErCUkCxlRPkNYoWTmCMGkHeUWI2vhJwgChdMVFsEYE7W3ZRbFkT9EvULQwnRBZLOlGTlIweyZ8Pn5bK1SUmywaflc=";


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Inject
    UserRepository userRepository;

    @Inject
    InitService initService;

    @PostConstruct
    public void prepareData() throws RestClientException {
        initService.initDatabase(false);
        createUser();
    }

    private void createUser() {
        User user = new User();
        user.setIsActive(true);
        user.setEmail(ADMIN_USER);
        user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        user.setPublicKey(PUBLIC_KEY);
        userRepository.save(user);
    }

}
