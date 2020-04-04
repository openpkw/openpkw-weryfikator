package org.openpkw.web.controllers

import com.jayway.jsonpath.JsonPath
import org.apache.commons.codec.binary.Base64
import org.openpkw.repositories.DistrictCommitteeRepository
import org.openpkw.repositories.ElectionCommitteeRepository
import org.openpkw.repositories.PeripheralCommitteeRepository
import org.openpkw.web.config.TestAppConfig
import org.openpkw.web.config.TestJpaConfig
import org.openpkw.web.configuration.MVCConfig
import org.openpkw.web.configuration.OAuth2ServerConfiguration
import org.openpkw.web.configuration.SecurityConfig
import org.openpkw.web.utils.DataLoader
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.inject.Inject

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

/**
 * @author Sebastian Pogorzelski
 */
@ContextConfiguration(classes = [TestJpaConfig, MVCConfig, SecurityConfig, OAuth2ServerConfiguration,TestAppConfig ])
@WebAppConfiguration
class AbstractOpenPKWSpec extends Specification {

    @Inject
    PeripheralCommitteeRepository peripheralCommitteeRepository

    @Inject
    DistrictCommitteeRepository districtCommitteeRepository

    @Inject
    ElectionCommitteeRepository electionCommitteeRepository

    @Inject
    WebApplicationContext context

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()

    }


    def String authenticate() throws Exception {
        def mvcResult = mockMvc.perform(post("/api/login")
                .param("username", DataLoader.ADMIN_USER)
                .param("password", DataLoader.ADMIN_PASSWORD)
                .param("grant_type", "password")
                .param("client_secret", "secret")
                .param("client_id", "openpkw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + new String(Base64.encodeBase64(("openpkw:secret").getBytes())))

        ).andReturn();
        def content = mvcResult.getResponse().getContentAsString()
        return JsonPath.read(content, "access_token")
    }


}