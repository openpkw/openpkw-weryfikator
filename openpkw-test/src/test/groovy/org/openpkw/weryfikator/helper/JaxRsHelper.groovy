package org.openpkw.weryfikator.helper

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.ClientProperties

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response

class JaxRsHelper {

    def static jsonSlurper = new JsonSlurper()

    /**
     * Creates JAX-RS client and configures timeouts for establishing connection and for reading response
     *
     * @param timeout timeout in seconds
     */
    def static createClient(int timeout = 5) {
        def configuration = new ClientConfig();
        configuration.property(ClientProperties.CONNECT_TIMEOUT, timeout * 1000);
        configuration.property(ClientProperties.READ_TIMEOUT, timeout * 1000);
        return ClientBuilder.newClient(configuration);
    }

    def static getResponseContent(Response response) {
        def data = response.readEntity(String)
        def json = null
        if (data) {
            json = jsonSlurper.parseText(data)
        }
        return new JsonResponseDTO(response.getStatus(), json)
    }

    def static toJson(content) {
        return JsonOutput.toJson(content)
    }
}