package org.openpkw.utils

import org.openpkw.mapping.PeripherialCommitteeLineMapper
import org.openpkw.model.entity.PeripheralCommittee
import org.openpkw.utils.csv.PeripheralCommitteeCsvParser
import spock.lang.Specification

class PeripheralCommitteeCsvParserSpec extends Specification {

    PeripheralCommitteeCsvParser csvParser = new PeripheralCommitteeCsvParser(
            new PeripherialCommitteeLineMapper())

    def "should parse file skipping failed lines"() {
        given:
        File tmpFile = new File('src/test/resources/pollstations.csv')

        when:
        List<PeripheralCommittee> result = csvParser.parse(tmpFile)

        then:
        result.size() == 2

        and:
        PeripheralCommittee firstLine = result[0]

        then:
        firstLine.territorialCode == '="020101"'

        and:
        PeripheralCommittee secondLine = result[1]

        then:
        with(secondLine) {
            peripheralCode == '2'
            territorialCode == '="020101"'
            type == 'gmina miejska'
            name == 'Szkoła Podstawowa Nr 3'
            allowedToVote == 1406L
        }
    }

    def "should not number allowed to vote maps to zero"() {
        given:
        File tmpFile = new File('src/test/resources/pollstations-unknown-allowed-to-vote.csv')

        when:
        def result = csvParser.parse(tmpFile)

        then:
        result[0].allowedToVote == 0L
    }

    def "should treat quotation marks as text"() {
        given:
        File tmpFile = new File('src/test/resources/pollstations-quotes.csv')

        when:
        def result = csvParser.parse(tmpFile)

        then:
        result[0].name == 'Muzeum Miejskie "Saturn'
    }

    def "should throw RuntimeException when file not found"() {
        given:
        File tmpFile = new File("unknown")

        when:
        csvParser.parse(tmpFile)

        then:
        thrown RuntimeException
    }
}