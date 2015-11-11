package org.openpkw.seatsinparliament;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SeatsInParliamentCalculatorServiceImpl implements SeatsInParliamentCalculatorService {

    @Override
    public Map<String, Integer> dHondt(int mandateInConstituency, Map<String, Integer> politicalPartyVoices) {

        Map<String, Integer> mandates = new LinkedHashMap<String, Integer>();
        Map<String, BigDecimal> calcTemp = new LinkedHashMap<String, BigDecimal>();

        Set<String> keySet2 = politicalPartyVoices.keySet();
        for (String string : keySet2) {
            calcTemp.put(string, new BigDecimal(politicalPartyVoices.get(string)));
        }

        BigDecimal max;
        String maxKey;
        Set<String> politicalPartys = calcTemp.keySet();
        for (String politicalParty : politicalPartys) {
            mandates.put(politicalParty, new Integer(0));
        }

        for (int i = 0; i < mandateInConstituency; i++) {
            max = new BigDecimal(-1);
            maxKey = null;
            for (String politicalParty : politicalPartys) {
                BigDecimal voices = (BigDecimal) calcTemp.get(politicalParty);
                if ((i + 1) == mandateInConstituency && max.compareTo(voices) == 0) {
                    if (maxKey == null) {
                        maxKey = politicalParty;
                    } else {
                        Integer voicesMax = politicalPartyVoices.get(maxKey);
                        Integer voicesTemp = politicalPartyVoices.get(politicalParty);
                        maxKey = (voicesMax > voicesTemp) ? maxKey : politicalParty;
                    }
                } else if (max.compareTo(voices) < 0) {
                    max = (BigDecimal) voices;
                    maxKey = politicalParty;
                }
            }
            mandates.put(maxKey, mandates.get(maxKey) + 1);
            BigDecimal a = new BigDecimal(politicalPartyVoices.get(maxKey));
            BigDecimal b = new BigDecimal(mandates.get(maxKey) + 1);
            calcTemp.replace(maxKey, (a.divide(b, 4, BigDecimal.ROUND_HALF_UP)));
        }

        return mandates;
    }
}