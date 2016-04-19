package org.openpkw.web.controllers

import org.springframework.test.web.servlet.MvcResult

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Sebastian Pogorzelski
 */
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