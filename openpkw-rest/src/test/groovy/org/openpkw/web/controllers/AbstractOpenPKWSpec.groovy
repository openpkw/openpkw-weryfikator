package org.openpkw.web.controllers

import com.jayway.jsonpath.JsonPath
import org.apache.commons.codec.binary.Base64
import org.openpkw.model.entity.User
import org.openpkw.repositories.DistrictCommitteeRepository
import org.openpkw.repositories.ElectionCommitteeRepository
import org.openpkw.repositories.PeripheralCommitteeRepository
import org.openpkw.repositories.UserRepository
import org.openpkw.services.init.InitService
import org.openpkw.web.config.TestAppConfig
import org.openpkw.web.config.TestJpaConfig
import org.openpkw.web.configuration.MVCConfig
import org.openpkw.web.configuration.OAuth2ServerConfiguration
import org.openpkw.web.configuration.SecurityConfig
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.inject.Inject
import javax.servlet.Filter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

/**
 * @author Sebastian Pogorzelski
 */
@ContextConfiguration(classes = [TestJpaConfig, MVCConfig, SecurityConfig, OAuth2ServerConfiguration,TestAppConfig ])
@WebAppConfiguration
class AbstractOpenPKWSpec extends Specification {

    static final String ADMIN_USER = "admin@openpkw.pl"
    static final String ADMIN_PASSWORD = "admin"

    static final String PRIVATE_KEY = "MIIBCQIBADAQBgcqhkjOPQIBBgUrgQQAJwSB8TCB7gIBAQRIAie7ReLT9TUheJy5S+h77VoMl1SIOFRkOmrTzrAWcXHeo4q5JhifwHAELPlO9wfiqfKvSQHszINFkJCN/8qBHpUHxPvN4AUAoAcGBSuBBAAnoYGVA4GSAAQESuzfne0O40uBLgBHwJ5oBMSZusvPX0XfkU8KoJ6PywEQSNjNSQ4z2z6Tqra32TMKh8RR1bGH/eWVIm759aoZE4xBZ1sI7+4BQMdC5/ErCUkCxlRPkNYoWTmCMGkHeUWI2vhJwgChdMVFsEYE7W3ZRbFkT9EvULQwnRBZLOlGTlIweyZ8Pn5bK1SUmywaflc="
    static final String PUBLIC_KEY = "MIGnMBAGByqGSM49AgEGBSuBBAAnA4GSAAQESuzfne0O40uBLgBHwJ5oBMSZusvPX0XfkU8KoJ6PywEQSNjNSQ4z2z6Tqra32TMKh8RR1bGH/eWVIm759aoZE4xBZ1sI7+4BQMdC5/ErCUkCxlRPkNYoWTmCMGkHeUWI2vhJwgChdMVFsEYE7W3ZRbFkT9EvULQwnRBZLOlGTlIweyZ8Pn5bK1SUmywaflc="


    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()


    @Inject
    PeripheralCommitteeRepository peripheralCommitteeRepository

    @Inject
    DistrictCommitteeRepository districtCommitteeRepository

    @Inject
    ElectionCommitteeRepository electionCommitteeRepository

    @Inject
    WebApplicationContext context

    @Inject
    Filter springSecurityFilterChain

    @Inject
    UserRepository userRepository

    @Inject
    InitService initService

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build()

    }



    def createUser() {
        User user = new User();
        user.setIsActive(true);
        user.setEmail(ADMIN_USER);
        user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        user.setPublicKey(PUBLIC_KEY)
        userRepository.save(user);
    }

    def String authenticate() throws Exception {
        def mvcResult = mockMvc.perform(post("/api/login")
                .param("username", ADMIN_USER)
                .param("password", ADMIN_PASSWORD)
                .param("grant_type", "password")
                .param("client_secret", "secret")
                .param("client_id", "openpkw")
                .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + new String(Base64.encodeBase64(("openpkw:secret").getBytes())))

        ).andReturn();
        def content = mvcResult.getResponse().getContentAsString()
        return JsonPath.read(content, "access_token")
    }

    def prepareData() {

        initService.initDatabase(true)
        createUser()
    }

}