package org.openpkw.utils.csv;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import au.com.bytecode.opencsv.CSVReader;

import org.codehaus.jackson.map.ObjectMapper;
import org.openpkw.utils.csv.json.*;

public class Main {

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    // Examle file
    public final static String EXAMPLE_CSV_FILE = "src/test/resources/p19x.csv";

    // Line numbers
    public final static int INDEX_LINE_COMMITTEE = 3;
    public final static int INDEX_LINE_CANDIDATE = 4;
    public final static int INDEX_FIRST_LINE_PERIPHERY = 5;

    // Column number
    public final static int INDEX_TERITORIAL_CODE = 1;
    public final static int INDEX_PERIPHERY_NUMBER = 5;

    public final static int INDEX_CARDS_FROM_BALLOT_BOX = 16;
    public final static int INDEX_CARDS_FROM_ENVELOPES = 17;
    public final static int INDEX_INVALID_CARD = 18;
    public final static int INDEX_INVALID_VOTES = 20;
    public final static int INDEX_VALID_CARD = 19;
    public final static int INDEX_VALID_VOTES = 21;
    public final static String URL_ADDRESS = "http://localhost";

    public static void main(String[] args) {
        try {
            List<PeripheryVote> peripheryVoteList = loadPeripheryVotesFromCsv(new File(EXAMPLE_CSV_FILE));
            for (PeripheryVote peripheryVote : peripheryVoteList)
                sendPeripheryVote(peripheryVote);
        } catch (Exception ex) {
            ex.printStackTrace();
            ///log.error("main()", ex);
        }
    }

    private static void sendPeripheryVote(PeripheryVote peripheryVote) throws Exception {

        Client client = Client.create();
        WebResource webResource = client.resource(URL_ADDRESS);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(peripheryVote);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        System.out.println("Output from Server .... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);

    }

    private static HashMap<Committee, HashMap<Integer, Candidate>> getMapCandidate(List<String[]> listAllFieldInFile) {
        HashMap<Committee, HashMap<Integer, Candidate>> hashMapCandidate = new HashMap<Committee, HashMap<Integer, Candidate>>();
        String isCandidateText = "Razem";
        String isCommitteeString = "\"=\"";

        Committee committee = null;
        for (int i = 0; i < listAllFieldInFile.get(INDEX_LINE_COMMITTEE).length; i++) {

            String committeeString = listAllFieldInFile.get(INDEX_LINE_COMMITTEE)[i];
            if (committeeString.indexOf(isCommitteeString) >= 0) {
                committee = new Committee();
                committee.setCommitteeName(committeeString.substring(committeeString.indexOf(isCommitteeString)).replace('"', ' ').replace('=', ' ').trim());
                committee.setCommitteeVotesNumber(0);
                hashMapCandidate.put(committee, new HashMap<Integer, Candidate>());
            }
            if (committee != null) {
                if (i + 1 < listAllFieldInFile.get(INDEX_LINE_CANDIDATE).length) {
                    String candidateString = listAllFieldInFile.get(INDEX_LINE_CANDIDATE)[i];
                    if (!candidateString.equals(isCandidateText)) {
                        Candidate candidate = new Candidate();
                        candidate.setCandidateName(candidateString.replace('=', ' ').replace('\"', ' ').trim());
                        candidate.setCandidateVotesNumber(0);
                        hashMapCandidate.get(committee).put(i, candidate);
                    }
                }
            }

        }
        return hashMapCandidate;

    }

    private static String getStringFromCsv(List<String[]> listAllFieldInFile, int line, int column) {
        return listAllFieldInFile.get(line)[column].replace('=', ' ').replace('\"', ' ').trim();
    }

    private static Long getLongFromCsv(List<String[]> listAllFieldInFile, int line, int column) {
        return Long.parseLong(listAllFieldInFile.get(line)[column]);
    }

    private static PeripheryVote getPeripheryVote(int line, HashMap<Committee, HashMap<Integer, Candidate>> mapCandidate, List<String[]> listAllFieldInFile) {
        PeripheryVote peripheryVote = new PeripheryVote();
        PeripheryVoteResults peripheryVoteResults = new PeripheryVoteResults();
        ArrayList<Committee> committeesList = new ArrayList<Committee>();
        peripheryVoteResults.setCommitteesList(committeesList);

        peripheryVoteResults.setPeripheryNumber(getLongFromCsv(listAllFieldInFile, line, INDEX_PERIPHERY_NUMBER));
        peripheryVoteResults.setTeritorialCode(getStringFromCsv(listAllFieldInFile, line, INDEX_TERITORIAL_CODE));
        VotingCards votingCards = new VotingCards();
        votingCards.setCardsFromBallotBox(getLongFromCsv(listAllFieldInFile, line, INDEX_CARDS_FROM_BALLOT_BOX));
        votingCards.setCardsFromEnvelopes(getLongFromCsv(listAllFieldInFile, line, INDEX_CARDS_FROM_ENVELOPES));
        votingCards.setInvalidCards(getLongFromCsv(listAllFieldInFile, line, INDEX_INVALID_CARD));
        votingCards.setInvalidVotes(getLongFromCsv(listAllFieldInFile, line, INDEX_INVALID_VOTES));
        votingCards.setValidCards(getLongFromCsv(listAllFieldInFile, line, INDEX_VALID_CARD));
        votingCards.setValidVotes(getLongFromCsv(listAllFieldInFile, line, INDEX_VALID_VOTES));
        peripheryVoteResults.setVotingCards(votingCards);

        for (Object key : mapCandidate.keySet().toArray()) {
            Committee committeeFromMap = (Committee) key;
            Committee committee = new Committee();
            committee.setCommitteeName(committeeFromMap.getCommitteeName());
            ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
            committee.setCandidate(candidateList);
            int minKey = Collections.min(mapCandidate.get(key).keySet());

            for (Object keyTwo : mapCandidate.get(committeeFromMap).keySet().toArray()) {
                Integer candidateIndex = (Integer) keyTwo;
                if (candidateIndex == minKey) {
                    committee.setCommitteeVotesNumber(Long.parseLong(listAllFieldInFile.get(line)[candidateIndex]));
                } else {
                    Candidate candidate = new Candidate();
                    candidate.setCandidateName(mapCandidate.get(committeeFromMap).get(candidateIndex).getCandidateName());
                    candidate.setCandidateVotesNumber(Long.parseLong(listAllFieldInFile.get(line)[candidateIndex]));
                    candidateList.add(candidate);
                }
            }
            committeesList.add(committee);

        }

        peripheryVote.setResults(peripheryVoteResults);

        return peripheryVote;
    }

    private static List<PeripheryVote> loadPeripheryVotesFromCsv(File file) {
        CSVReader reader = null;
        ArrayList<PeripheryVote> peripheryVoteList = new ArrayList<PeripheryVote>();
        try {
            reader = new CSVReader(new FileReader(file), ';', '\'');
            List<String[]> listAllFieldInFile = reader.readAll();
            HashMap<Committee, HashMap<Integer, Candidate>> mapCandidate = getMapCandidate(listAllFieldInFile);
            for (int line = INDEX_FIRST_LINE_PERIPHERY; line < listAllFieldInFile.size(); line++)
                peripheryVoteList.add(getPeripheryVote(line, mapCandidate, listAllFieldInFile));
            // ObjectMapper mapper = new ObjectMapper();
            // mapper.defaultPrettyPrintingWriter().writeValue(System.out, peripheryVoteList.get(0));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file " + file.getName() + ": " + ex.getMessage(), ex);

        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception ex) {
                log.warn("Failed to close file.", ex);
            }
        }
        return peripheryVoteList;
    }

}
