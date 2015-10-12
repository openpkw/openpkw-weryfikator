package org.openpkw.seatsinparliament;

import java.util.Map;

public interface SeatsInParliamentCalculatorService {

	public Map<String, Integer> dHondt(int mandateInConstituency,
			Map<String, Integer> politicalPartyVoices);
	
}
