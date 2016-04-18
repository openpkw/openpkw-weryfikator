package org.openpkw.web.controllers

import org.openpkw.web.config.TestAppConfig
import org.openpkw.web.config.TestJpaConfig
import org.openpkw.web.configuration.MVCConfig
import org.openpkw.web.configuration.OAuth2ServerConfiguration
import org.openpkw.web.configuration.SecurityConfig
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MvcResult

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Sebastian Pogorzelski
 */
@ContextConfiguration(classes = [TestJpaConfig, MVCConfig, SecurityConfig, OAuth2ServerConfiguration,TestAppConfig ])
@WebAppConfiguration
class DistrictControllerSpec extends AbstractOpenPKWSpec {

    def "should get all districts"(){
        //given:

        when:
        MvcResult mvcResult = mockMvc.perform(get('/districts')
                .contentType(MediaType.APPLICATION_JSON)
                //.header("Authorization", "Bearer " + token)  //add security token
        ).andExpect(status().isOk()).andReturn();

        then:
        mvcResult.getResponse().getContentAsString() != null

    }

}