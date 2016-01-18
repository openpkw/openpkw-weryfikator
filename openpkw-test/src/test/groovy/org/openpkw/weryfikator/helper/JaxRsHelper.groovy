package org.openpkw.weryfikator.helper

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.ClientProperties

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response

class JaxRsHelper {

    def static jsonSlurper = new JsonSlurper()

    def static createClient() {
        def configuration = new ClientConfig();
        configuration.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        configuration.property(ClientProperties.READ_TIMEOUT, 5000);
        return ClientBuilder.newClient(configuration);

    }

    def static getResponseContent(Response response) {
        return new JsonResponseDTO(response.getStatus(), jsonSlurper.parseText(response.readEntity(String)))
    }

    def static toJson(content) {
        return JsonOutput.toJson(content)
    }

}