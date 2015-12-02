package org.openpkw.web.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openpkw.web.controllers.TestController;

/**
 *
 * @author Waldemar
 */
public class EchoControllerTest {

    public EchoControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of echo method, of class EchoController.
     */
    @Test
    public void testEcho() {
        System.out.println("echo");
        Map<String, String> object = null;
        TestController instance = new TestController();
        Map<String, String> expResult = null;
        Map<String, String> result = instance.echo(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}