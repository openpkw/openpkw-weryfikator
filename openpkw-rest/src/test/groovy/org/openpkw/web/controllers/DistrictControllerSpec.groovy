package org.openpkw.web.controllers

import com.jayway.jsonpath.JsonPath
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
        def content = mvcResult.getResponse().getContentAsString()

        content != null

        def districts = JsonPath.read(content, "districts")
        districts != null
        print(districts.length)
    }

}