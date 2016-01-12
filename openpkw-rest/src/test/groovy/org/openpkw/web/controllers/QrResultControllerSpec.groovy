package org.openpkw.web.controllers

import com.jayway.jsonpath.JsonPath
import groovy.json.JsonOutput
import org.apache.commons.codec.binary.Base64
import org.openpkw.model.entity.*
import org.openpkw.services.qr.dto.QrDTO
import org.openpkw.repositories.CandidateRepository
import org.openpkw.repositories.DistrictCommitteeRepository
import org.openpkw.repositories.ElectionCommitteeRepository
import org.openpkw.repositories.PeripheralCommitteeRepository
import org.openpkw.repositories.UserRepository
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
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.inject.Inject
import javax.servlet.Filter
import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [TestJpaConfig, MVCConfig, TestAppConfig, SecurityConfig, OAuth2ServerConfiguration])
@WebAppConfiguration
class QrResultControllerSpec extends Specification {

    static final String QR_CODE = '146519,332,19,2086,1760,103,1657,1,18,0,0,0,0,0,0,0,1656,0,0,1656,12,1644,,0101;272,0102;38,0103;16,0104;6,0105;8,0106;6,0107;7,0108;4,0109;2,0201;366,0202;31,0203;21,0204;12,0205;7,0206;1,0207;33,0208;2,0209;6,0210;3,0301;78,0302;2,0303;1,0304;4,0305;1,0306;1,0310;2,0401;58,0402;3,0403;8,0404;1,0501;50,0504;5,0505;2,0510;1,0601;249,0602;13,0603;1,0605;2,0609;1,0610;7,0701;1,0702;1'
    static final String ADMIN_USER = "admin@openpkw.pl";
    static final String ADMIN_PASSWORD = "admin";

    @Inject
    WebApplicationContext context

    @Inject
    PeripheralCommitteeRepository peripheralCommitteeRepository

    @Inject
    DistrictCommitteeRepository districtCommitteeRepository

    @Inject
    CandidateRepository candidateRepository

    @Inject
    ElectionCommitteeRepository electionCommitteeRepository

    @Inject
    UserRepository userRepository;

    @Inject
    Filter springSecurityFilterChain;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build()
        prepareData()
    }

    private prepareData() {
        createUser()

        DistrictCommitteeAddress districtCommitteeAddress = new DistrictCommitteeAddress(
                name: 'Name',
                street: '12',
                buildingNumber: '1',
                city: 'Warsaw',
                postalCode: '00-021',
                post: 'Warsaw'
        )
        DistrictCommittee districtCommittee = new DistrictCommittee(
                districtCommitteeAddress: districtCommitteeAddress,
                districtCommitteeNumber: 19,
                name: "Name"
        )
        PeripheralCommittee peripheralCommittee = new PeripheralCommittee(
                districtCommittee: districtCommittee,
                peripheralCommitteeNumber: 332,
                territorialCode: "146519"
        )

        districtCommitteeRepository.save(districtCommittee);
        peripheralCommitteeRepository.save(peripheralCommittee)

        createCandidates(districtCommittee)
    }

    private createUser() {
        User user = new User();
        user.setIsActive(true);
        user.setEmail(ADMIN_USER);
        user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        userRepository.save(user);
    }

    private createCandidates(DistrictCommittee districtCommittee) {

        (1..10).each { listNumber ->

            ElectionCommittee electionCommittee = new ElectionCommittee(name: "Commitee + ${listNumber}")

            ElectionCommitteeDistrict electionCommitteeDistrict = new ElectionCommitteeDistrict(
                    listNumber: listNumber,
                    electionCommitteeId: electionCommittee,
                    districtCommittee: districtCommittee
            )
            electionCommitteeDistrict.candidateCollection = (1..10).collect { numberOnList ->
                new Candidate(
                            positionOnList: numberOnList,
                            electionCommitteeDistrict: electionCommitteeDistrict
                    )
            }
            electionCommittee.electionCommitteeDistrictCollection.add(electionCommitteeDistrict)

            electionCommitteeRepository.save(electionCommittee);
        }
    }

    def "should save result to database"() {
        given:
        def dto = new QrDTO(QR_CODE, '')
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

    String authenticate() throws Exception {
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
}
