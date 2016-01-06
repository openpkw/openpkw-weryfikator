package org.openpkw.utils.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.openpkw.utils.csv.json.Candidate;
import org.openpkw.utils.csv.json.Comments;
import org.openpkw.utils.csv.json.Committee;
import org.openpkw.utils.csv.json.CorrespondenceVoting;
import org.openpkw.utils.csv.json.DokumentGeneratorRequest;
import org.openpkw.utils.csv.json.ElectoralCampaign;
import org.openpkw.utils.csv.json.PeripheryVote;
import org.openpkw.utils.csv.json.PeripheryVoteResults;
import org.openpkw.utils.csv.json.VotingCards;

import au.com.bytecode.opencsv.CSVReader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Main {

    private final static Logger log = Logger.getLogger(Main.class);

    private final static DecimalFormat df = new DecimalFormat("0000");
    private final static DecimalFormat dfPerformance = new DecimalFormat("0.00");

    // Examle file
    public final static String EXAMPLE_CSV_FILE = "/p19x_new.csv";

    // Line numbers
    public final static int INDEX_COLUMNS_LINE = 0;
    public final static int INDEX_FIRST_LINE_PERIPHERY = 1;

    // Column number
    public final static int INDEX_TeritorialCode = 2;
    public final static int INDEX_PeripheryNumber = 4;
    public final static int INDEX_Commune = 1;
    public final static int INDEX_PeriphealCommissionAddress = 3;

    public final static int INDEX_TotalEntitledToVote = 5;
    public final static int INDEX_TotalCards = 6;
    public final static int INDEX_UnusedCards = 7;

    public final static int INDEX_RegularVoters = 8;
    public final static int INDEX_RepresentativeVoters = 9;
    public final static int INDEX_CertificateVoters = 10;
    public final static int INDEX_CardsFromBallotBox = 18;
    public final static int INDEX_CardsFromEnvelopes = 19;
    public final static int INDEX_InvalidCards = 20;
    public final static int INDEX_ValidCards = 21;
    public final static int INDEX_InvalidVotes = 22;
    public final static int INDEX_ValidVotes = 26;

    public final static int INDEX_IssuedPackages = 11;
    public final static int INDEX_ReceivedReplyEnvelopes = 12;
    public final static int INDEX_MissingStatement = 13;
    public final static int INDEX_MissingSignatureOnStatement = 14;
    public final static int INDEX_MissingEnvelopeForVotingCard = 15;
    public final static int INDEX_UnsealedEnvelope = 16;
    public final static int INDEX_EnvelopesThrownToBallotBox = 17;

    public final static String URL_ADDRESS_LOCALHOST = "http://localhost:8080/openpkw-dokument-generator/service/protocol";
    public final static String URL_ADDRESS_DEV = "http://rumcajs.open-pkw.pl:9080/openpkw-dokument-generator/service/protocol";
    public final static String URL_ADDRESS_UAT = "http://dobromir.openpkw.pl:9080/openpkw-dokument-generator/service/protocol";

    public static void main(String[] args) {
        try {
            List<PeripheryVote> peripheryVoteList = loadPeripheryVotesFromCsv(EXAMPLE_CSV_FILE);
            System.out.println("Found data for " + peripheryVoteList.size() + " peripheries");
            long startTime = Calendar.getInstance().getTimeInMillis();

            for (PeripheryVote peripheryVote : peripheryVoteList) {
                sendPeripheryVote(peripheryVote);
            }

            long endTime = Calendar.getInstance().getTimeInMillis();
            long duration = endTime - startTime;
            double averageProcessingTime = 60 * 1000 * (double) peripheryVoteList.size() / (double) duration;
            System.out.println("Done. Duration: " + (duration / 1000) + " seconds. Average processing time: " + dfPerformance.format(averageProcessingTime) + " PDF/min");
        } catch (Exception ex) {
            log.error("main()", ex);
        }
    }

    private static void sendPeripheryVote(PeripheryVote peripheryVote) throws Exception {
        String territorialCode = peripheryVote.getResults().getTerritorialCode();
        String commune = peripheryVote.getResults().getCommune();
        long peripheryNumber = peripheryVote.getResults().getPeripheryNumber();
        long startTime = Calendar.getInstance().getTimeInMillis();
        System.out.print("Processing periphery number " + peripheryNumber + " in " + commune + "... ");

        String outputFileName = "output-" + territorialCode + "-" + df.format(peripheryNumber) + ".pdf";
        if (new File(outputFileName).exists()) {
            System.out.println("Skipping.");
            return;
        }
        Client client = Client.create();
        WebResource webResource = client.resource(URL_ADDRESS_DEV);
        ObjectMapper mapper = new ObjectMapper();
        DokumentGeneratorRequest request = new DokumentGeneratorRequest("2015_parliament_periphery", peripheryVote);
        String json = mapper.writeValueAsString(request);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);

        if (response.getStatus() != 200) {
            String responseBody = response.getEntity(String.class);
            System.err.println(responseBody);
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        long endTime = Calendar.getInstance().getTimeInMillis();
        long duration = endTime - startTime;

        System.out.println("Done. Duration: " + duration + " ms.");
        byte[] pdfFile = response.getEntity(byte[].class);
        FileOutputStream out = new FileOutputStream(outputFileName);
        out.write(pdfFile);
        out.close();
    }

    private static HashMap<Committee, HashMap<Integer, Candidate>> getMapCandidate(List<String[]> listAllFieldInFile) {
        HashMap<Committee, HashMap<Integer, Candidate>> hashMapCandidate = new HashMap<Committee, HashMap<Integer, Candidate>>();
        String isCommiteeEnd = "Razem";
        String isCommitteeStart = "KW";
        boolean startCommittee = false;
        HashMap<Integer, Candidate> candidateHashMap = null;
        Committee committee = null;
        int committeeNumber = 0;
        for (int i = 0; i < listAllFieldInFile.get(INDEX_COLUMNS_LINE).length; i++) {
            String value = listAllFieldInFile.get(INDEX_COLUMNS_LINE)[i];
            if (startCommittee) {
                if (value.indexOf(isCommiteeEnd) == 0) {
                    startCommittee = false;
                    if (committee != null)
                        committee.setTotalCandidatesVotesNumber(i);
                } else {
                    Candidate candidate = new Candidate();
                    candidate.setCandidateName(value);
                    candidateHashMap.put(i, candidate);
                }

            } else {
                if (value.indexOf(isCommitteeStart) == 0) {
                    candidateHashMap = new HashMap<Integer, Candidate>();
                    committeeNumber++;
                    committee = new Committee();
                    committee.setCommitteeNumber(committeeNumber);
                    committee.setCommitteeName(value);
                    committee.setCommitteeVotesNumber(i);
                    hashMapCandidate.put(committee, candidateHashMap);
                    startCommittee = true;
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

        peripheryVoteResults.setPeripheryNumber(getLongFromCsv(listAllFieldInFile, line, INDEX_PeripheryNumber));
        peripheryVoteResults.setTerritorialCode(getStringFromCsv(listAllFieldInFile, line, INDEX_TeritorialCode));
        peripheryVoteResults.setPeripheralCommissionAddress(getStringFromCsv(listAllFieldInFile, line, INDEX_PeriphealCommissionAddress));
        peripheryVoteResults.setCommune(getStringFromCsv(listAllFieldInFile, line, INDEX_Commune)); // TODO: get from CVS (gmina)
        peripheryVoteResults.setCounty("?"); // TODO: get from CVS (powiat) - brak w CSV
        peripheryVoteResults.setProvince("?"); // TODO: get from CVS (wojewodztwo) - brak w CSV
        peripheryVoteResults.setDistrictNumber("19"); // TODO: get from CVS (nr okregu) - brak w CSV
        peripheryVoteResults.setDistrictCommissionAddress("?"); // TODO: get from CVS (adres okregowej komisji) - brak w CSV

        ElectoralCampaign electoralCampaign = new ElectoralCampaign();
        electoralCampaign.setDate("2015-09-23");
        electoralCampaign.setStartTime("07:00");
        electoralCampaign.setEndTime("21:00");

        peripheryVoteResults.setElectoralCampaign(electoralCampaign);

        VotingCards votingCards = new VotingCards();
        votingCards.setTotalEntitledToVote(getLongFromCsv(listAllFieldInFile, line, INDEX_TotalEntitledToVote));
        votingCards.setTotalCards(getLongFromCsv(listAllFieldInFile, line, INDEX_TotalCards));
        votingCards.setUnusedCards(getLongFromCsv(listAllFieldInFile, line, INDEX_UnusedCards));
        votingCards.setRegularVoters(getLongFromCsv(listAllFieldInFile, line, INDEX_RegularVoters));
        votingCards.setRepresentativeVoters(getLongFromCsv(listAllFieldInFile, line, INDEX_RepresentativeVoters));
        votingCards.setCertificateVoters(getLongFromCsv(listAllFieldInFile, line, INDEX_CertificateVoters));
        votingCards.setCardsFromBallotBox(getLongFromCsv(listAllFieldInFile, line, INDEX_CardsFromBallotBox));
        votingCards.setCardsFromEnvelopes(getLongFromCsv(listAllFieldInFile, line, INDEX_CardsFromEnvelopes));
        votingCards.setInvalidCards(getLongFromCsv(listAllFieldInFile, line, INDEX_InvalidCards));
        votingCards.setValidCards(getLongFromCsv(listAllFieldInFile, line, INDEX_ValidCards));
        votingCards.setInvalidVotes(getLongFromCsv(listAllFieldInFile, line, INDEX_InvalidVotes));
        votingCards.setValidVotes(getLongFromCsv(listAllFieldInFile, line, INDEX_ValidVotes));
        peripheryVoteResults.setVotingCards(votingCards);

        CorrespondenceVoting correspondenceVoting = new CorrespondenceVoting();
        correspondenceVoting.setIssuedPackages(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_IssuedPackages));
        correspondenceVoting.setReceivedReplyEnvelopes(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_ReceivedReplyEnvelopes));
        correspondenceVoting.setMissingStatement(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_MissingStatement));
        correspondenceVoting.setMissingSignatureOnStatement(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_MissingSignatureOnStatement));
        correspondenceVoting.setMissingEnvelopeForVotingCard(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_MissingEnvelopeForVotingCard));
        correspondenceVoting.setUnsealedEnvelope(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_UnsealedEnvelope));
        correspondenceVoting.setEnvelopesThrownToBallotBox(getLongFromCsv(listAllFieldInFile, line, Main.INDEX_EnvelopesThrownToBallotBox));
        peripheryVoteResults.setCorrespondenceVoting(correspondenceVoting);

        List<Committee> commiteeList = Arrays.asList(mapCandidate.keySet().toArray(new Committee[0]));
        Collections.sort(commiteeList, new Comparator<Committee>() {
            @Override
            public int compare(Committee o1, Committee o2) {
                return ((Long) o1.getCommitteeNumber()).compareTo((Long) o2.getCommitteeNumber());
            }

        });

        for (Committee committeeFromMap : commiteeList) {
            Committee committee = new Committee();
            committee.setCommitteeName(committeeFromMap.getCommitteeName());
            committee.setCommitteeNumber(committeeFromMap.getCommitteeNumber());
            committee.setCommitteeVotesNumber(getLongFromCsv(listAllFieldInFile, line, (int) committeeFromMap.getCommitteeVotesNumber()));
            committee.setTotalCandidatesVotesNumber((getLongFromCsv(listAllFieldInFile, line, (int) committeeFromMap.getTotalCandidatesVotesNumber())));

            ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
            committee.setCandidate(candidateList);
            List<Integer> keys = Arrays.asList(mapCandidate.get(committeeFromMap).keySet().toArray(new Integer[0]));
            Collections.sort(keys);

            for (int candidateIndex : keys) {
                Candidate candidate = new Candidate();
                candidate.setCandidateName(mapCandidate.get(committeeFromMap).get(candidateIndex).getCandidateName());
                candidate.setCandidateVotesNumber(Long.parseLong(listAllFieldInFile.get(line)[candidateIndex]));
                candidateList.add(candidate);

            }

            committeesList.add(committee);
        }

        Comments comments = new Comments();

        comments.setComments15("Brak uwag.");
        comments.setComments16("Brak uwag.");
        comments.setComments17("Brak uwag.");
        comments.setComments18("Brak zarządzeń.");
        comments.setComments19("Brak zarzutów.");
        comments.setComments20("Brak zarzutów.");
        comments.setComments21("Brak uwag.");

        peripheryVoteResults.setComments(comments);

        peripheryVote.setResults(peripheryVoteResults);

        return peripheryVote;
    }

    private static List<PeripheryVote> loadPeripheryVotesFromCsv(String fileName) {
        CSVReader reader = null;
        ArrayList<PeripheryVote> peripheryVoteList = new ArrayList<PeripheryVote>();
        try {
            reader = new CSVReader(new InputStreamReader(Main.class.getResourceAsStream(fileName)), ',', '\"');
            List<String[]> listAllFieldInFile = reader.readAll();
            HashMap<Committee, HashMap<Integer, Candidate>> mapCandidate = getMapCandidate(listAllFieldInFile);
            for (int line = INDEX_FIRST_LINE_PERIPHERY; line < listAllFieldInFile.size(); line++) {
                peripheryVoteList.add(getPeripheryVote(line, mapCandidate, listAllFieldInFile));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file " + fileName + ": " + ex.getMessage(), ex);

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
