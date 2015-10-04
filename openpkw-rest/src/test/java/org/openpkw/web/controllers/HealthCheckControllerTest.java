package org.openpkw.web.controllers;

import com.jayway.jsonpath.JsonPath;
import org.openpkw.web.config.TestAppConfig;
import org.openpkw.web.config.TestJpaConfig;
import org.openpkw.web.configuration.MVCConfig;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Security test for health check controller
 * @author Sebastian Pogorzelski
 */
@ContextConfiguration(classes = {TestJpaConfig.class,TestAppConfig.class, MVCConfig.class})
@WebAppConfiguration
public class HealthCheckControllerTest extends AbstractTestNGSpringContextTests {

    @Inject
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeClass
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldAuthenticate() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/api/authenticate").param("username", "admin@openpkw.pl").param("password", "admin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("token").exists())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        String token = JsonPath.read(content, "token");
        System.out.println(token);
    }

    @Test
    public void shouldGetHealthCheckSimpleTest() throws Exception {
        mvc.perform(get("/api/healthCheck/simpleTest")).andExpect(status().isOk());
    }

}
