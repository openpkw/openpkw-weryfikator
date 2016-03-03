package org.openpkw.services.init;

import org.openpkw.model.entity.*;
import org.openpkw.repositories.*;
import org.openpkw.services.init.dto.InitDTO;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openpkw.services.init.parse.*;
import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by mrozi on 12.01.16.
 */
@Service public class InitServiceImpl implements InitService {

    private final static Logger log = (Logger) LoggerFactory.getLogger(InitServiceImpl.class);
    private final static String FILE_NAME_DISTRICTS = "/districts.csv";
    private final static String FILE_NAME_PERIPHERALS = "/peripherals.csv";
    private final static String FILE_NAME_CANDIDATES = "/candidates.csv";

    private List<DistrictCommittee> districtCommitteeList;
    private List<PeripheralCommittee> peripheralCommitteeList;
    private List<Candidate> candidateList;
    private List<ElectionCommittee> electionCommitteeList;
    private List<ElectionCommitteeDistrict> electionCommitteeDistrictList;

    @Qualifier("districtCommitteeRepository")
    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;

    @Qualifier("districtCommitteeAddressRepository")
    @Inject
    private DistrictCommitteeAddressRepository districtCommitteeAddressRepository;

    @Qualifier("peripheralCommitteeRepository")
    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;

    @Qualifier("peripherialCommitteeAddressRepository")
    @Inject
    private PeripherialCommitteeAddressRepository peripherialCommitteeAddressRepository;

    @Qualifier("electionCommitteeRepository")
    @Inject
    private ElectionCommitteeRepository electionCommitteeRepository;

    @Qualifier("electionCommitteeDistrictRepository")
    @Inject
    private ElectionCommitteeDistrictRepository electionCommitteeDistrictRepository;

    @Qualifier("electionCommitteeVoteRepository")
    @Inject
    private ElectionCommitteeVoteRepository electionCommitteeVoteRepository;

    @Qualifier("candidateRepository")
    @Inject
    private CandidateRepository candidateRepository;

    @Qualifier("protocolRepository")
    @Inject
    private ProtocolRepository protocolRepository;

    @Qualifier("voteRepository")
    @Inject
    private VoteRepository voteRepository;

    @Override public InitDTO initDatabase(boolean deleteDatabase) throws RestClientException {

        if (deleteDatabase) {
            deleteDatabase();
        }
        if (!deleteDatabase && isAlreadyInit()) {
            throw new RestClientException(RestClientErrorMessage.DATABASE_ALREADY_INIT);
        }
        readCsvFiles();
        writeToDatabase();
        return getResult();
    }

    private void deleteDatabase() {
        electionCommitteeVoteRepository.deleteAllInBatch();
        voteRepository.deleteAllInBatch();
        candidateRepository.deleteAllInBatch();
        protocolRepository.deleteAllInBatch();
        peripheralCommitteeRepository.deleteAllInBatch();
        peripherialCommitteeAddressRepository.deleteAllInBatch();
        ;
        electionCommitteeDistrictRepository.deleteAllInBatch();
        electionCommitteeRepository.deleteAllInBatch();
        districtCommitteeRepository.deleteAllInBatch();
        districtCommitteeAddressRepository.deleteAllInBatch();
    }

    private InitDTO getResult() {
        InitDTO initDTO = new InitDTO();
        initDTO.setCandidateCount(candidateRepository.count());
        initDTO.setDistrictCommitteeCount(districtCommitteeRepository.count());
        initDTO.setDistrictCommitteeAddresCount(districtCommitteeAddressRepository.count());
        initDTO.setPeripheralCommitteCount(peripheralCommitteeRepository.count());
        initDTO.setPeripheralCommitteeAddressCount(peripherialCommitteeAddressRepository.count());
        initDTO.setElectionCommitteeCount(electionCommitteeRepository.count());
        initDTO.setElectionCommitteeDistrictCount(electionCommitteeDistrictRepository.count());
        return initDTO;
    }

    private boolean isAlreadyInit() {
        return districtCommitteeRepository.count() > 0 ||
                districtCommitteeAddressRepository.count() > 0 ||
                peripheralCommitteeRepository.count() > 0 ||
                peripherialCommitteeAddressRepository.count() > 0 ||
                electionCommitteeRepository.count() > 0 ||
                electionCommitteeDistrictRepository.count() > 0 ||
                candidateRepository.count() > 0;
    }

    public void readCsvFiles() {
        readDistrictCommitteeListFromCsv(FILE_NAME_DISTRICTS);
        readPeripheralCommiteeFromCsv(FILE_NAME_PERIPHERALS);
        readCandidateListFromCsv(FILE_NAME_CANDIDATES);
        extractCandidateList();
    }

    public void writeToDatabase() {
        log.info("Starting writing to the database");
        writeToDatabaseDistrictCommitteeAndAddress();
        writeToDatabaseDistrictPeripheralCommitteeAndAdress();
        writeToDatabaseElectionCommittee();
        writeToDatabaseElectionDistrictCommittee();
        writeToDatabaseCandidate();

        log.info("Finished writing to the database");
    }

    private void writeToDatabaseDistrictCommitteeAndAddress() {
        log.info("Writing list of district committees to the database");
        for (DistrictCommittee districtCommittee : this.districtCommitteeList) {
            districtCommitteeRepository.save(districtCommittee);
            districtCommitteeAddressRepository.save(districtCommittee.getDistrictCommitteeAddress());

        }
    }

    private void writeToDatabaseDistrictPeripheralCommitteeAndAdress() {
        log.info("Writing list of district peripheral committees to the database");
        for (PeripheralCommittee peripheralCommittee : this.peripheralCommitteeList) {
            //peripherialCommitteeAddressRepository.save(peripheralCommittee.getPeripheralCommitteeAddress());
            peripheralCommitteeRepository.save(peripheralCommittee);
        }
    }

    private void writeToDatabaseElectionCommittee() {
        log.info("Writing list of election committees to the database");
        for (ElectionCommittee electionCommittee : this.electionCommitteeList) {
            electionCommitteeRepository.save(electionCommittee);
        }
    }

    private void writeToDatabaseElectionDistrictCommittee() {
        log.info("Writing list of election comimittee districts to the database");
        for (ElectionCommitteeDistrict electionCommitteeDistrict : this.electionCommitteeDistrictList) {
            electionCommitteeDistrictRepository.save(electionCommitteeDistrict);
        }
    }

    private void writeToDatabaseCandidate() {
        log.info("Writing list of candidates to the database");
        for (Candidate candidate : this.candidateList) {
            candidateRepository.save(candidate);
        }
    }

    private static String getStringFromCsv(List<String[]> listAllFieldInFile, int line, int column) {
        return listAllFieldInFile.get(line)[column].trim();
    }

    private static Integer getIntFromCsv(List<String[]> listAllFieldInFile, int line, int column) {
        return Integer.parseInt(listAllFieldInFile.get(line)[column]);
    }

    private PeripheralCommittee getPeripheralCommitte(int line, List<String[]> listAllFieldInFile, List<DistrictCommittee> districtCommitteeList) {
        PeripheralCommitteeAddress peripheralCommitteeAddress = new PeripheralCommitteeAddress();
        peripheralCommitteeAddress.setName(getStringFromCsv(listAllFieldInFile, line, PeripheralCsvLine.AddressName.getLineNumber()));

        PeripheralCommittee peripheralCommittee = new PeripheralCommittee();
        peripheralCommittee.setPeripheralCommitteeAddress(peripheralCommitteeAddress);
        peripheralCommittee.setPeripheralCommitteeNumber(getIntFromCsv(listAllFieldInFile, line, PeripheralCsvLine.Number.getLineNumber()));
        peripheralCommittee.setTerritorialCode(getStringFromCsv(listAllFieldInFile, line, PeripheralCsvLine.TeritorialCode.getLineNumber()));
        peripheralCommittee.setName(getStringFromCsv(listAllFieldInFile, line, PeripheralCsvLine.Name.getLineNumber()));

        int committeeNumber = getIntFromCsv(listAllFieldInFile, line, PeripheralCsvLine.DistrictNumber.getLineNumber());
        DistrictCommittee districtCommittee = districtCommitteeList.stream().filter(a -> a.getDistrictCommitteeNumber() == committeeNumber).findFirst().get();

        peripheralCommittee.setDistrictCommittee(districtCommittee);
        return peripheralCommittee;
    }

    private DistrictCommittee getDistrictCommittee(int line, List<String[]> listAllFieldInFile) {
        DistrictCommitteeAddress districtCommitteeAddress = new DistrictCommitteeAddress();
        districtCommitteeAddress.setCity(getStringFromCsv(listAllFieldInFile, line, DistrictCsvLine.City.getLineNumber()));
        DistrictCommittee districtCommittee = new DistrictCommittee();
        districtCommittee.setDistrictCommitteeAddress(districtCommitteeAddress);
        districtCommittee.setDistrictCommitteeNumber(getIntFromCsv(listAllFieldInFile, line, DistrictCsvLine.Number.getLineNumber()));
        districtCommittee.setName(getStringFromCsv(listAllFieldInFile, line, DistrictCsvLine.Name.getLineNumber()));
        return districtCommittee;
    }

    private void readPeripheralCommiteeFromCsv(String fileName) {
        log.info("Loading data from " + fileName);
        peripheralCommitteeList = new ArrayList<PeripheralCommittee>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)), ',', '\"');
            List<String[]> listAllFieldInFile = reader.readAll();
            for (int i = 0; i < listAllFieldInFile.size(); i++) {
                peripheralCommitteeList.add(getPeripheralCommitte(i, listAllFieldInFile, districtCommitteeList));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file " + fileName + ": " + ex.getMessage(), ex);

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                log.warn("Failed to close file.", ex);
            }
        }
        log.info("Loaded " + peripheralCommitteeList.size() + " peripheral committees.");
    }

    private Candidate getCandidate(int line, List<String[]> listAllFieldInFile, List<DistrictCommittee> districtCommitteeList) {
        ElectionCommittee electionCommittee = new ElectionCommittee();
        electionCommittee.setName(getStringFromCsv(listAllFieldInFile, line, CandidateCsvLine.ElectionCommiteeListName.getLineNumber()));

        ElectionCommitteeDistrict electionCommitteeDistrict = new ElectionCommitteeDistrict();
        electionCommitteeDistrict.setElectionCommitteeId(electionCommittee);
        electionCommitteeDistrict.setListNumber(getIntFromCsv(listAllFieldInFile, line, CandidateCsvLine.ElectionCommitteeListNumber.getLineNumber()));
        int committeeNumber = getIntFromCsv(listAllFieldInFile, line, CandidateCsvLine.DistrictNumber.getLineNumber());
        DistrictCommittee districtCommittee = districtCommitteeList.stream().filter(a -> a.getDistrictCommitteeNumber() == committeeNumber).findFirst().get();
        electionCommitteeDistrict.setDistrictCommittee(districtCommittee);

        Candidate candidate = new Candidate();
        candidate.setElectionCommitteeDistrict(electionCommitteeDistrict);
        candidate.setName(getStringFromCsv(listAllFieldInFile, line, CandidateCsvLine.Name.getLineNumber()));
        candidate.setSurname(getStringFromCsv(listAllFieldInFile, line, CandidateCsvLine.Surname.getLineNumber()));
        candidate.setPositionOnList(getIntFromCsv(listAllFieldInFile, line, CandidateCsvLine.Number.getLineNumber()));

        return candidate;
    }

    private void extractCandidateList() {
        log.info("Extracting list of candidates");
        electionCommitteeList = new ArrayList<ElectionCommittee>();
        electionCommitteeDistrictList = new ArrayList<ElectionCommitteeDistrict>();
        for (Candidate candidate : candidateList) {
            Optional<ElectionCommitteeDistrict> findDistrict = electionCommitteeDistrictList.stream().filter(a -> a.getDistrictCommittee().getDistrictCommitteeNumber() == candidate.getElectionCommitteeDistrict().getDistrictCommittee().getDistrictCommitteeNumber() && a.getListNumber() == candidate.getElectionCommitteeDistrict().getListNumber()).findFirst();

            if (findDistrict.isPresent()) {
                candidate.setElectionCommitteeDistrict(findDistrict.get());
            } else {
                Optional<ElectionCommittee> find = electionCommitteeList.stream().filter(a -> a.getName().trim().equals(candidate.getElectionCommitteeDistrict().getElectionCommitteeId().getName().trim())).findFirst();
                if (find.isPresent()) {
                    electionCommitteeDistrictList.add(candidate.getElectionCommitteeDistrict());
                    candidate.getElectionCommitteeDistrict().setElectionCommitteeId(find.get());
                } else {
                    electionCommitteeList.add(candidate.getElectionCommitteeDistrict().getElectionCommitteeId());
                    electionCommitteeDistrictList.add(candidate.getElectionCommitteeDistrict());
                }
            }
        }
        log.info("Extracted " + electionCommitteeList.size() + " election committees and " + electionCommitteeDistrictList.size() + " election committee districts.");
    }

    private void readCandidateListFromCsv(String fileName) {
        log.info("Loading data from " + fileName);
        candidateList = new ArrayList<Candidate>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)), ',', '\"');
            List<String[]> listAllFieldInFile = reader.readAll();
            for (int i = 0; i < listAllFieldInFile.size(); i++) {
                candidateList.add(getCandidate(i, listAllFieldInFile, districtCommitteeList));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file " + fileName + ": " + ex.getMessage(), ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                log.warn("Failed to close file.", ex);
            }
        }
        log.info("Loaded " + candidateList.size() + " candidates.");
    }

    private void readDistrictCommitteeListFromCsv(String fileName) {
        log.info("Loading data from " + fileName);
        CSVReader reader = null;
        districtCommitteeList = new ArrayList<DistrictCommittee>();
        try {
            reader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)), ',', '\"');
            List<String[]> listAllFieldInFile = reader.readAll();
            for (int i = 0; i < listAllFieldInFile.size(); i++) {
                districtCommitteeList.add(getDistrictCommittee(i, listAllFieldInFile));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file " + fileName + ": " + ex.getMessage(), ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                log.warn("Failed to close file.", ex);
            }
        }
        log.info("Loaded " + districtCommitteeList.size() + " district committees.");
    }

        /*private void verifyUpload() {
            log.info("Verifying data upload");
            em = factory.createEntityManager();
            getNumberOfRows("candidates", "from Candidate c");
            getNumberOfRows("peripheral committees", "from PeripheralCommittee pc");
            getNumberOfRows("district committees", "from DistrictCommittee dc");
            getNumberOfRows("election committees", "from ElectionCommittee ec");
            log.info("Data upload verification complete.");
        }

        private void getNumberOfRows(String entityName, String queryText) {
            log.info("Loading " + entityName + ".");
            try {
                Query query = em.createQuery(queryText);
                List<?> result = query.getResultList();
                log.info("Loaded " + result.size() + " " + entityName + ".");
            } catch (Exception ex) {
                log.error("Failed to read " + entityName + ": " + ex.getMessage(), ex);
            }
        }*/

}
