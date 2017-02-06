package org.openpkw.web.controllers

import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetupTest
import groovy.json.JsonOutput
import org.junit.Rule
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
    public static final String FROM_EMAIL_ADDRESS = "admin@openpkw.pl"

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP)

    @Inject
    PasswordChangeRequestRepository changeRequestRepository

    @Inject
    DataLoader dataLoader

    @IgnoreIf({ SpringProfileHelper.integrationTestsDisabled() })
    def "should create change password request and send email to user"() {
        given:
        dataLoader.createUser(TEST_EMAIL, "current_pass", DataLoader.PUBLIC_KEY)
        def dto = new ChangePasswordRequestDTO().withEmail(TEST_EMAIL)
        def content = JsonOutput.toJson(dto)

        when:
        mockMvc.perform(put('/api/account/password')
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())

        and:
        def changeReqOptional = changeRequestRepository.findByUser_Email(TEST_EMAIL)

        then:
        changeReqOptional.isPresent()
        changeReqOptional.get().token != null

        greenMail.getReceivedMessages().size() == 1
        greenMail.getReceivedMessagesForDomain(TEST_EMAIL) == 1
        greenMail.getReceivedMessagesForDomain(TEST_EMAIL).first()
        greenMail.getReceivedMessagesForDomain(TEST_EMAIL).first().getFrom().contains(FROM_EMAIL_ADDRESS)
        ((String)greenMail.getReceivedMessagesForDomain(TEST_EMAIL).first().getContent()).contains("user")


    }

}