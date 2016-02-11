package org.openpkw.weryfikator.e2e

import org.openpkw.weryfikator.helper.JaxRsHelper
import org.openpkw.weryfikator.rest.Configuration
import spock.lang.Specification

import javax.ws.rs.client.Entity

class EchoServicesSpec extends Specification {

    def static TEST_STRING = "OpenPKW rules"

    def "should return the same content that was sent"() {

        given:
        def testContent = JaxRsHelper.toJson([test: TEST_STRING])

        when:
        def target = JaxRsHelper.createClient().target(Configuration.getHost() + "/test/echo");
        def response = target.request().post(Entity.json(testContent));
        def result = JaxRsHelper.getResponseContent(response)

        then:

        result.getStatus() == 200
        result.jsonObject.test == TEST_STRING
    }

}