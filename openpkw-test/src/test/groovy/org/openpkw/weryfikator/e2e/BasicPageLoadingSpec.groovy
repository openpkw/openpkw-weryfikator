package org.openpkw.weryfikator.e2e

import org.openpkw.weryfikator.helper.JaxRsHelper
import org.openpkw.weryfikator.rest.Configuration
import spock.lang.Specification


class BasicPageLoadingSpec extends Specification {

    def "should load start page"() {
        given:
        def client = JaxRsHelper.createClient();
        def target = client.target(Configuration.getHost());

        when:
        def response = target.request().get(String.class);

        then:
        response.indexOf("Hello World!")>=0

    }
}