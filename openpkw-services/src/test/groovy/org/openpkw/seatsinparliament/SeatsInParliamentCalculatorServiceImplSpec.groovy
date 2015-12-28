package org.openpkw.seatsinparliament

import org.openpkw.services.seatsinparliament.SeatsInParliamentCalculatorServiceImpl
import spock.lang.Specification
import spock.lang.Unroll

class SeatsInParliamentCalculatorServiceImplSpec extends Specification {

    SeatsInParliamentCalculatorServiceImpl seatsInParliamentCalculatorServiceImpl = new SeatsInParliamentCalculatorServiceImpl()

    @Unroll
    def "should return #expected dHoundt for mandate #mandate and party voices #partyVoices"(int mandate, Map<String, Integer> partyVoices, Map<String, Integer> expected) {
        when:
        def dHondt = seatsInParliamentCalculatorServiceImpl.dHondt(mandate, partyVoices)

        then:
        dHondt == expected

        where:
        mandate | partyVoices                                          || expected
        10      | ['A': 1000,  'B': 400, 'C': 500, 'D': 600, 'E': 100] || ['A': 5, 'B': 1, 'C': 2, 'D': 2, 'E': 0]
        7       | ['A': 1228, 'B': 1012, 'C': 850, 'D': 543, 'E': 352] || ['A': 2, 'B': 2, 'C': 2, 'D': 1, 'E': 0]
    }
}
