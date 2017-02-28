package org.openpkw.web.controllers

import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetupTest
import groovy.json.JsonOutput
import org.junit.Rule
import org.openpkw.repositories.PasswordChangeRequestRepository
import org.openpkw.services.password.dto.ChangePasswordRequestDTO
import org.openpkw.web.utils.DataLoader
import org.openpkw.web.utils.SpringProfileHelper
import spock.lang.IgnoreIf

import javax.inject.Inject
import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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

    @Override
    def setup() {
        dataLoader.createUserIfNotExists(TEST_EMAIL, "current_pass", DataLoader.PUBLIC_KEY)
    }

    @IgnoreIf({ SpringProfileHelper.integrationTestsDisabled() })
    def "should create change password request and send email to user"() {
        given:
        def dto = new ChangePasswordRequestDTO().withEmail(TEST_EMAIL)
        def content = JsonOutput.toJson(dto)

        when:
        createChangePasswordRequest(content)

        and:
        def changeReqOptional = changeRequestRepository.findByUser_EmailAndActiveTrue(TEST_EMAIL)

        then:
        changeReqOptional.isPresent()
        changeReqOptional.get().token != null

        greenMail.getReceivedMessages().size() == 1
        greenMail.getReceivedMessagesForDomain(TEST_EMAIL).length == 1
        greenMail.getReceivedMessagesForDomain(TEST_EMAIL).first()
        greenMail.getReceivedMessagesForDomain(TEST_EMAIL).first().getFrom().first().toString() == FROM_EMAIL_ADDRESS
        ((String)greenMail.getReceivedMessagesForDomain(TEST_EMAIL).first().getContent()).contains("Witaj")


    }

//    @IgnoreIf({ SpringProfileHelper.integrationTestsDisabled() })
//    def "should get data to change password form"() {
//        given:
//        def dto = new ChangePasswordRequestDTO().withEmail(TEST_EMAIL)
//        def content = JsonOutput.toJson(dto)
//        createChangePasswordRequest(content)
//        def changeReqOptional = changeRequestRepository.findByUser_EmailAndActiveTrue(TEST_EMAIL)
//        def token = changeReqOptional.get().token
//
//        when:
//        def mvcResult = mockMvc.perform(get('/api/account/password/' + token)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn()
//
//        then:
//        mvcResult.response.contentAsString == ''
//    }



    @IgnoreIf({ SpringProfileHelper.integrationTestsDisabled() })
    def "should change password"() {
        given:
        def dto = new ChangePasswordRequestDTO().withEmail(TEST_EMAIL)
        def content = JsonOutput.toJson(dto)
        createChangePasswordRequest(content)

        def changeReqOptional = changeRequestRepository.findByUser_EmailAndActiveTrue(TEST_EMAIL)
        def token = changeReqOptional.get().token

        def passwordChangeDto = new ChangePasswordRequestDTO().withEmail(TEST_EMAIL).withToken(token).withPassword("NewPassword")

        when:
        def mvcResult = mockMvc.perform(post('/api/account/password/' + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(passwordChangeDto)))
                .andExpect(status().isOk()).andReturn()

        then:
        mvcResult.response.contentAsString == ''
    }

    def createChangePasswordRequest(String content) {
        mockMvc.perform(put('/api/account/password')
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
    }

}