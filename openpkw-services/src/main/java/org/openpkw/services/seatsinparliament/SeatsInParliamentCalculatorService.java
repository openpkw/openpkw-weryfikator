package org.openpkw.services.seatsinparliament;

import java.util.Map;

public interface SeatsInParliamentCalculatorService {

    Map<String, Integer> dHondt(int mandateInConstituency, Map<String, Integer> politicalPartyVoices);

}