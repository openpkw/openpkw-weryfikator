package org.openpkw.web.controllers

import groovy.json.JsonOutput
import org.openpkw.model.entity.PasswordChangeRequest
import org.openpkw.repositories.PasswordChangeRequestRepository
import org.openpkw.services.password.dto.ChangePasswordRequestDTO
import org.openpkw.web.utils.DataLoader
import org.openpkw.web.utils.SpringProfileHelper
import org.springframework.test.web.servlet.MvcResult
import spock.lang.IgnoreIf

import javax.inject.Inject
import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ChangePasswordControllerSpec extends AbstractOpenPKWSpec {


    public static final String TEST_EMAIL = "change_pass@opkw.pl"

    @Inject
    PasswordChangeRequestRepository changeRequestRepository

    @Inject
    DataLoader dataLoader

    @IgnoreIf({ SpringProfileHelper.integrationTestsDisabled() })
    def "should create change password request"() {
        given:
        dataLoader.createUser(TEST_EMAIL, "current_pass", DataLoader.PUBLIC_KEY)
        def dto = new ChangePasswordRequestDTO().withEmail(TEST_EMAIL)
        def content = JsonOutput.toJson(dto)

        when:
        mockMvc.perform(put('/api/account/password')
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())

        and:
        def changeReqOptional = changeRequestRepository.findByUser_Email(TEST_EMAIL)

        then:
        changeReqOptional.isPresent()
        changeReqOptional.get().token != null

    }

}