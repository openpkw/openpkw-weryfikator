package org.openpkw.web.controllers;

import java.security.SecureRandom;
import java.util.Date;

import javax.transaction.Transactional;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.model.entity.UserType;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.Autorize;
import org.openpkw.web.Token;
import org.openpkw.web.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@OpenPKWAPIController
@Transactional
@RequestMapping("/authorize")
public class LoginController {

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDeviceRepository deviceRepository;

    // @RequestParam(value="txtEmail", required=false)
    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    @Transactional
    /*
     * public String register( @RequestParam(value="clientPublicKey" ) String clientPublicKey, @RequestParam(value="devid") String devid,
     * 
     * @RequestParam(value="email") String email, @RequestParam(value="password") String password){ Autorize autorize = new Autorize(clientPublicKey, devid, email, password);
     */
    public String register(@RequestBody UserRegister userRegister) {
        User user = userRepository.findByEmailAddress("fdsaf");
        System.out.println("L:" + userRegister + ", user:" + user);
        if (userRegister == null || userRegister.isEmpty()) {
            return "ERROR";
        }

        // User user = userRepository.findByEmailAddress(autorize.getEmail());
        user = userRepository.findByEmailAddress(userRegister.getEmail());
        if (user != null) {
            return "ERROR";
        }
        user = new User();
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        user.setFirstName(userRegister.getFirst_name());
        user.setLastName(userRegister.getLast_name());
        user.setIsActive(true);
        user.setUserType(userRegister.getUserType());

        try {
            userRepository.saveAndFlush(user);
            System.out.println("" + user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR.";
        }
        return "OK";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Token login(Autorize autorize) {
        System.out.println(autorize);
        Token token = new Token();
        User user = userRepository.findByEmailAddress(autorize.getEmail());
        if (user == null) {
            user = new User(autorize.getEmail(), autorize.getPassword());
            user.setUserType(UserType.VOLUNTEER);
            user.setIsActive(Boolean.TRUE);
            user.setTokenCreatedDate(new Date());
            userRepository.saveAndFlush(user);
        }

        UserDevice device = deviceRepository.findByUserIdAndDevId(user.getUserID(), autorize.getDevid());
        if (device == null) {
            device = new UserDevice();
            device.setDevId(autorize.getDevid());
            device.setUser(user);
        }
        token = updateUserToken(device);
        deviceRepository.saveAndFlush(device);

        return token;
    }

    private Token updateUserToken(UserDevice device) {
        byte[] bToken = new byte[24];
        Token token = new Token();
        secureRandom.nextBytes(bToken);
        token.setToken(bToken);
        device.setToken(token.getToken());
        return token;
    }
}