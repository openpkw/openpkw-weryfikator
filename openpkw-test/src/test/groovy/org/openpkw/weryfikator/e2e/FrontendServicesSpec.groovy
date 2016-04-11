package org.openpkw.weryfikator.e2e

import org.openpkw.weryfikator.helper.JaxRsHelper
import org.openpkw.weryfikator.rest.Configuration
import spock.lang.Specification
import static org.openpkw.weryfikator.invoker.UserServicesInvoker.*

class FrontendServicesSpec extends Specification {

    def static final OK_STATUS = 200
    def hostUrl = Configuration.getHost()

    def "getting data for the whole country"() {

        given:
        def restProxy = JaxRsHelper.createClient(30)

        when:
        def response = restProxy.target(hostUrl + "/votes").request().get()

        then:
        response.getStatus() == OK_STATUS

        def responseData = JaxRsHelper.getResponseContent(response)
        responseData.jsonObject.errorMessage == null

        responseData.jsonObject.protocolNumber > 0
        responseData.jsonObject.protocolAllNumber > 0
        responseData.jsonObject.votersVoteNumber > 0
        responseData.jsonObject.voteCommittees.size > 0
    }

    def "getting data for specific district"() {

        given:
        def restProxy = JaxRsHelper.createClient(30)

        when:
        def response = restProxy.target(hostUrl + "/districtVotes/1").request().get()

        then:
        response.getStatus() == OK_STATUS

        def responseData = JaxRsHelper.getResponseContent(response)
        responseData.jsonObject.errorMessage == null
        responseData.jsonObject.protocolNumber > 0
        responseData.jsonObject.protocolAllNumber > 0
        responseData.jsonObject.votersVoteNumber > 0
        responseData.jsonObject.allVoteCommittees.size > 0
    }
}