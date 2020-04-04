package org.openpkw.web.controllers

import groovy.json.JsonOutput
import org.openpkw.model.entity.Candidate
import org.openpkw.repositories.CandidateRepository
import org.openpkw.services.qr.dto.QrDTO
import org.openpkw.services.sign.SignService
import org.openpkw.web.utils.DataLoader
import org.openpkw.web.utils.SpringProfileHelper
import org.springframework.test.web.servlet.MvcResult
import spock.lang.IgnoreIf

import javax.inject.Inject
import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class QrResultControllerSpec extends AbstractOpenPKWSpec {

    static final String QR_CODE = '146519,332,19,2086,1760,103,1657,1,18,0,0,0,0,0,0,0,1656,0,0,1656,12,1644,,0101;272,0102;38,0103;16,0104;6,0105;8,0106;6,0107;7,0108;4,0109;2,0201;366,0202;31,0203;21,0204;12,0205;7,0206;1,0207;33,0208;2,0209;6,0210;3,0301;78,0302;2,0303;1,0304;4,0305;1,0306;1,0310;2,0401;58,0402;3,0403;8,0404;1,0501;50,0504;5,0505;2,0510;1,0601;249,0602;13,0603;1,0605;2,0609;1,0610;7,0701;1,0702;1'


    @Inject
    CandidateRepository candidateRepository


    @Inject
    SignService signService

    //@IgnoreIf({ SpringProfileHelper.integrationTestsDisabled() })
    def "should save result to database"() {
        given:
        def signature = Base64.getEncoder().encodeToString(signService.generateSignature(QR_CODE, signService.getPrivateKeyFromBase64(DataLoader.PRIVATE_KEY)))
        def dto = new QrDTO(QR_CODE, signature)

        def content = JsonOutput.toJson(dto)

        def token = authenticate()

        when:
        MvcResult mvcResult = mockMvc.perform(post('/api/qr')
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)  //add security token
                        .content(content)
        ).andExpect(status().isOk()).andReturn();

        then:
        mvcResult.getResponse().getContentAsString() != null

        and:
        List<Candidate> candidates = candidateRepository.findAll()

        then:
        candidates != null
        !candidates.isEmpty()
    }

}
