package org.openpkw.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openpkw.model.entity.*;
import org.openpkw.qr.dto.QrDTO;
import org.openpkw.repositories.CandidateRepository;
import org.openpkw.repositories.DistrictCommitteeRepository;
import org.openpkw.repositories.ElectionCommitteeRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.web.config.TestAppConfig;
import org.openpkw.web.config.TestJpaConfig;
import org.openpkw.web.configuration.MVCConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for QRResultController
 * @author Sebastian Pogorzelski
 */
@ContextConfiguration(classes = {TestJpaConfig.class, TestAppConfig.class, MVCConfig.class})
@WebAppConfiguration
public class QrResultControllerTest extends AbstractTestNGSpringContextTests {

    private static final String QR_CODE = "146519,332,2086,1760,103,1657,1,18,0,0,0,0,0,0,0,1656,0,0,1656,12,1644,,0101;272,0102;38,0103;16,0104;6,0105;8,0106;6,0107;7,0108;4,0109;2,0110;4,0111;4,0112;7,0113;3,0114;17,0115;4,0116;1,0119;3,0120;4,0121;2,0124;1,0128;2,0129;1,0130;1,0131;5,0132;1,0133;7,0137;3,0138;2,0139;1,0140;2,0201;366,0202;31,0203;21,0204;12,0205;7,0206;1,0207;33,0208;2,0209;6,0210;3,0211;3,0212;5,0213;2,0214;2,0218;2,0219;3,0220;1,0222;1,0223;14,0224;1,0227;1,0229;1,0235;1,0236;1,0237;1,0238;1,0240;5,0301;78,0302;2,0303;1,0304;4,0305;1,0306;1,0310;2,0320;1,0326;1,0401;58,0402;3,0403;8,0404;1,0412;1,0420;1,0421;1,0424;1,0426;1,0501;50,0504;5,0505;2,0510;1,0511;2,0512;1,0601;249,0602;13,0603;1,0605;2,0609;1,0610;7,0611;2,0612;1,0614;4,0615;1,0616;3,0617;3,0621;1,0623;1,0626;1,0628;1,0629;1,0632;1,0635;1,0636;1,0637;2,0639;2,0701;1,0702;1";

    @Inject
    private WebApplicationContext context;

    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;

    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private ElectionCommitteeRepository electionCommitteeRepository;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        prepareData();
    }

    private void prepareData() {
        DistrictCommitteeAddress districtCommitteeAddress = new DistrictCommitteeAddress("Street", "12", "1", "Warsaw", "00-021", "Warsaw");
        DistrictCommittee districtCommittee = new DistrictCommittee();
        districtCommittee.setDistrictCommitteeAddressId(districtCommitteeAddress);
        districtCommittee.setDistrictCommitteeNumber(1);
        districtCommittee.setName("Name");

        districtCommitteeRepository.save(districtCommittee);

        peripheralCommitteeRepository.save(createPeripheralCommittee(332, "146519", districtCommittee));

        createCandidates(districtCommittee);
    }

    private void createCandidates(DistrictCommittee districtCommittee) {

        for (int listNumber = 1; listNumber <= 20; listNumber++) {

            ElectionCommittee electionCommittee = new ElectionCommittee();
            electionCommittee.setName("Commitee " + listNumber);

            ElectionCommitteeDistrict electionCommitteeDistrict = new ElectionCommitteeDistrict();
            electionCommitteeDistrict.setListNumber(listNumber);
            electionCommitteeDistrict.setElectionCommitteeId(electionCommittee);
            electionCommitteeDistrict.setDistrictCommitteeId(districtCommittee);
            electionCommittee.getElectionCommitteeDistrictCollection().add(electionCommitteeDistrict);

            for (int numberOnList = 1; numberOnList <= 40; numberOnList++) {
                Candidate candidate = new Candidate();
                candidate.setPositionOnList(numberOnList);
                candidate.setElectionCommitteeDistrictId(electionCommitteeDistrict);
                electionCommitteeDistrict.getCandidateCollection().add(candidate);

            }
            electionCommitteeRepository.save(electionCommittee);
        }
    }

    private PeripheralCommittee createPeripheralCommittee(int peripheralCommitteeNumber, String territorialCode, DistrictCommittee districtCommittee) {
        PeripheralCommittee peripheralCommittee = new PeripheralCommittee();
        peripheralCommittee.setDistrictCommitteeId(districtCommittee);
        peripheralCommittee.setPeripheralCommitteeNumber(peripheralCommitteeNumber);
        peripheralCommittee.setTerritorialCode(territorialCode);
        return peripheralCommittee;
    }

    @Test
    public void shouldSaveResultToDatabase() throws Exception {

        QrDTO dto = new QrDTO(QR_CODE, "");

        String content = convertObjectToJsonBytes(dto);
        MvcResult mvcResult = mockMvc.perform(post("/api/qr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Qr saved");

        List<Candidate> candidates = candidateRepository.findAll();
        for (Candidate candidate: candidates) {
            candidate.getVoteCollection();
        }
        assertThat(candidates).isNotNull();
        assertThat(candidates).isNotEmpty();

    }


    private String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
