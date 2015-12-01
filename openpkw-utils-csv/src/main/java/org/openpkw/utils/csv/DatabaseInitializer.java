package org.openpkw.utils.csv;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.openpkw.model.entity.Candidate;
import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.DistrictCommitteeAddress;
import org.openpkw.model.entity.ElectionCommittee;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.openpkw.model.entity.PeripheralCommittee;
import org.openpkw.model.entity.PeripheralCommitteeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class DatabaseInitializer {
    private EntityManagerFactory factory = null;
    private EntityManager em = null;
    private final String PERSISTENCE_UNIT_NAME = "openpkw";
    private final static Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final static String FILE_NAME_DISTRICTS = "/districts.csv";
    private final static String FILE_NAME_PERIPHERALS = "/peripherals.csv";
    private final static String FILE_NAME_CANDIDATES = "/candidates.csv";

    private List<DistrictCommittee> districtCommitteeList;
    private List<PeripheralCommittee> peripheralCommitteeList;
    private List<Candidate> candidateList;
    private List<ElectionCommittee> electionCommitteeList;
    private List<ElectionCommitteeDistrict> electionCommitteeDistrictList;

    public enum DistrictCsvLine {
        City(2), 
        Number(1), 
        Name(3);

        private int lineNumber;

        private DistrictCsvLine(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    public enum PeripheralCsvLine {
        DistrictNumber(0), 
        Name(4), 
        Number(3), 
        TeritorialCode(1), 
        AddressName(4);

        private int lineNumber;

        private PeripheralCsvLine(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    public enum CandidateCsvLine {
        DistrictNumber(0), 
        ElectionCommitteeListNumber(1), 
        ElectionCommiteeListName(2), 
        Number(3), 
        Surname(4), 
        Name(5);

        private int lineNumber;

        private CandidateCsvLine(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    private void setupEntityManagerAndInitTransacation() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    private void commitTransacationAndClose() {
        log.info("Committing the database transaction");
        em.getTransaction().commit();
        em.close();
        log.info("Data upload completed");
    }

    public static void main(String[] argv) {
        try {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();
            databaseInitializer.readCsvFiles();
            databaseInitializer.writeToDatabase();
        } catch (Exception ex) {
            log.error("main()", ex);
        }
    }

    public void readCsvFiles() {
        readDistrictCommitteeListFromCsv(FILE_NAME_DISTRICTS);
        readPeripheralCommiteeFromCsv(FILE_NAME_PERIPHERALS);
        readCandidateListFromCsv(FILE_NAME_CANDIDATES);
        extractCandidateList();
    }

    public void writeToDatabase() {
        setupEntityManagerAndInitTransacation();
        writeToDatabaseDistrictCommitteeAndAddress();
        writeToDatabaseDistrictPeripheralCommitteeAndAdress();
        writeToDatabaseElectionCommittee();
        writeToDatabaseElectionDistrictCommittee();
        writeToDatabaseCandidate();
        commitTransacationAndClose();
    }

    private void writeToDatabaseDistrictCommitteeAndAddress() {
        log.info("Writing list of district committees to the database");
        for (DistrictCommittee districtCommittee : this.districtCommitteeList) {
            em.persist(districtCommittee.getDistrictCommitteeAddressId());
            em.persist(districtCommittee);
        }
    }

    private void writeToDatabaseDistrictPeripheralCommitteeAndAdress() {
        log.info("Writing list of district peripheral committees to the database");
        for (PeripheralCommittee peripheralCommittee : this.peripheralCommitteeList) {
            em.persist(peripheralCommittee.getPeripheralCommitteeAddress());
            em.persist(peripheralCommittee);
        }
    }

    private void writeToDatabaseElectionCommittee() {
        log.info("Writing list of election committees to the database");
        for (ElectionCommittee electionCommittee : this.electionCommitteeList) {
            em.persist(electionCommittee);
        }
    }

    private void writeToDatabaseElectionDistrictCommittee() {
        log.info("Writing list of election comimittee districts to the database");
        for (ElectionCommitteeDistrict electionCommitteeDistrict : this.electionCommitteeDistrictList) {
            em.persist(electionCommitteeDistrict);
        }
    }

    private void writeToDatabaseCandidate() {
        log.info("Writing list of candidates to the database");
        for (Candidate candidate : this.candidateList) {
            em.persist(candidate);
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

        peripheralCommittee.setDistrictCommitteeId(districtCommittee);
        return peripheralCommittee;
    }

    private DistrictCommittee getDistrictCommittee(int line, List<String[]> listAllFieldInFile) {
        DistrictCommitteeAddress districtCommitteeAddress = new DistrictCommitteeAddress();
        districtCommitteeAddress.setCity(getStringFromCsv(listAllFieldInFile, line, DistrictCsvLine.City.getLineNumber()));
        DistrictCommittee districtCommittee = new DistrictCommittee();
        districtCommittee.setDistrictCommitteeAddressId(districtCommitteeAddress);
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
    }

    private Candidate getCandidate(int line, List<String[]> listAllFieldInFile, List<DistrictCommittee> districtCommitteeList) {
        ElectionCommittee electionCommittee = new ElectionCommittee();
        electionCommittee.setName(getStringFromCsv(listAllFieldInFile, line, CandidateCsvLine.ElectionCommiteeListName.getLineNumber()));

        ElectionCommitteeDistrict electionCommitteeDistrict = new ElectionCommitteeDistrict();
        electionCommitteeDistrict.setElectionCommitteeId(electionCommittee);
        electionCommitteeDistrict.setListNumber(getIntFromCsv(listAllFieldInFile, line, CandidateCsvLine.ElectionCommitteeListNumber.getLineNumber()));
        int committeeNumber = getIntFromCsv(listAllFieldInFile, line, CandidateCsvLine.DistrictNumber.getLineNumber());
        DistrictCommittee districtCommittee = districtCommitteeList.stream().filter(a -> a.getDistrictCommitteeNumber() == committeeNumber).findFirst().get();
        electionCommitteeDistrict.setDistrictCommitteeId(districtCommittee);

        Candidate candidate = new Candidate();
        candidate.setElectionCommitteeDistrictId(electionCommitteeDistrict);
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
            Optional<ElectionCommitteeDistrict> findDistrict = electionCommitteeDistrictList.stream().filter(a -> a.getDistrictCommitteeId().getDistrictCommitteeNumber() == candidate.getElectionCommitteeDistrictId().getDistrictCommitteeId().getDistrictCommitteeNumber() && a.getListNumber() == candidate.getElectionCommitteeDistrictId().getListNumber()).findFirst();

            if (findDistrict.isPresent()) {
                candidate.setElectionCommitteeDistrictId(findDistrict.get());
            } else {
                Optional<ElectionCommittee> find = electionCommitteeList.stream().filter(a -> a.getName().trim().equals(candidate.getElectionCommitteeDistrictId().getElectionCommitteeId().getName().trim())).findFirst();
                if (find.isPresent()) {
                    electionCommitteeDistrictList.add(candidate.getElectionCommitteeDistrictId());
                    candidate.getElectionCommitteeDistrictId().setElectionCommitteeId(find.get());
                } else {
                    electionCommitteeList.add(candidate.getElectionCommitteeDistrictId().getElectionCommitteeId());
                    electionCommitteeDistrictList.add(candidate.getElectionCommitteeDistrictId());
                }
            }
        }
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
    }
}