package org.openpkw.web.controllers;

import java.security.SecureRandom;
import java.util.List;
import javax.ws.rs.QueryParam;
import org.openpkw.model.entity.User;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Tomasz Łabuz on 2015-07-17.
 */
@OpenPKWAPIController
@RequestMapping("/authorize")
public class LoginController {
				private final SecureRandom secureRandom = new SecureRandom();
				
				@Autowired
				UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Token register(@QueryParam( "CLIENT_PUBLIC_KEY" ) String clPublicKey,@QueryParam( "LOGIN" ) String login,@QueryParam( "PASSWORD" ) String password){
					Token token = new Token();
					List<User> users = userRepository.findByEmailAndPassword(login, password);
					if( users==null && users.isEmpty() ){
						token.error();
					}else{
						User user = users.get(0);
						token = updateUserToken(user);
						try{
							userRepository.saveAndFlush(user);
						}catch(Exception ex){
							token.error();
						}
					}
					return token;
				}
				
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Token login(@QueryParam( "CLIENT_PUBLIC_KEY" ) String clPublicKey,@QueryParam( "LOGIN" ) String login,@QueryParam( "PASSWORD" ) String password){
					System.out.println("PublicKey:"+clPublicKey+" LOGIN"+login+" PASSWORD:"+password);
					Token token = new Token();
					List<User> users = userRepository.findByEmailAndPassword(login,password);
					if( users==null && users.isEmpty() ){
						token.error();
					}else{
						User user = users.get(0);
						token = updateUserToken(user);
						userRepository.saveAndFlush(user);
					}
					
					return token;
    }
				
				private Token updateUserToken(User user){
						byte[] bToken = new byte[24];
						Token token = new Token();
						secureRandom.nextBytes(bToken);
						token.setToken(bToken);
						user.setToken(token.getToken());
						return token;
				}
				
}