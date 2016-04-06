package org.openpkw.weryfikator.e2e

import org.openpkw.weryfikator.helper.JaxRsHelper
import org.openpkw.weryfikator.rest.Configuration
import spock.lang.Specification
import static org.openpkw.weryfikator.invoker.UserServicesInvoker.*

class FrontendServicesSpec extends Specification {

    def static final OK_STATUS = 200;
    def static final VOTES_URL = "/votes";

    def "should return data for the whole country"() {

        when:
        def response = callApi(VOTES_URL)

        then:
        response.getStatus() == OK_STATUS

        def responseData = JaxRsHelper.getResponseContent(response)
        responseData.jsonObject.errorMessage == null

        responseData.jsonObject.protocolNumber > 0
        responseData.jsonObject.protocolAllNumber > 0
        responseData.jsonObject.votersVoteNumber > 0
        responseData.jsonObject.allVotersNumber > 0
        responseData.jsonObject.voteCommittees.size > 0
    }

    def callApi(String url) {
        def fullURL = Configuration.getHost() + url
        def target = JaxRsHelper.createClient(30).target(fullURL)
        return target.request().get()
    }
}