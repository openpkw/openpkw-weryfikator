//package org.openpkw.web.controllers;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//import java.util.Map;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openpkw.web.controllers.UsersController;
//import org.openpkw.web.dto.UserCredentialsDTO;
//import org.openpkw.web.dto.NewUserDTO;
//import org.openpkw.web.dto.Token;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
///**
// *
// * @author Waldemar
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration
//public class LoginControllerTest {
//
//	@Autowired
//	private UsersController loginController;
//
//	public LoginControllerTest() {
//	}
//
//	@BeforeClass
//	public static void setUpClass() {
//	}
//
//	@AfterClass
//	public static void tearDownClass() {
//	}
//
//	@Before
//	public void setUp() {
//	}
//
//	@After
//	public void tearDown() {
//	}
//
//	/**
//	 * Test of register method, of class LoginController.
//	 */
//	/*
//	http://localhost:8080/openpkw/authorize/register
//	application/json
//		{
//"clientPublicKey": "fdsgf",
//"devid": "dsafdsafgdsg4rg4g54ghy54hg334",
//"email": "gfds",
//"password": "gfds"
//}
//	*/
//	@Test
//	public void testRegister() {
//		System.out.println("register");
//		NewUserDTO userRegister = null;
//		String expResult = "";
//		ResponseEntity<Map<String, String>> result = loginController.register(userRegister);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//}
