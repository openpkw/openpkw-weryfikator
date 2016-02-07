package org.openpkw.weryfikator.invoker

import org.openpkw.weryfikator.helper.JaxRsHelper
import org.openpkw.weryfikator.helper.JsonResponseDTO
import org.openpkw.weryfikator.rest.Configuration

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Form
import javax.ws.rs.core.Response

class UserServicesInvoker {

    def static createUser(String testContent) {
        def client = JaxRsHelper.createClient()
        def target = client.target(Configuration.getHost() + "/users/")
        def response = target.request().post(Entity.json(testContent), Response);

        return JaxRsHelper.getResponseContent(response)
    }

    def static getUser(String email) {
        def client = JaxRsHelper.createClient()
        def target = client.target(Configuration.getHost() + "/users/" + email)
        def response = target.request().get(Response)

        return JaxRsHelper.getResponseContent(response)
    }

    def static deleteUser(String email) {
        def client = JaxRsHelper.createClient()
        def target = client.target(Configuration.getHost() + "/users/" + email)
        def response = target.request().delete(Response)

        return JaxRsHelper.getResponseContent(response)
    }

    def static generateEmail() {
        return Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com"
    }

    def static createUser(String email, String password) {
        return createUser(JaxRsHelper.toJson([email: email, password: password, first_name: "first_name", last_name: "last_name"]))
    }

    def static createUser(String email, String password, String publicKey) {
        return createUser(JaxRsHelper.toJson([email: email, password: password, first_name: "first_name", last_name: "last_name", public_key: publicKey]))
    }

    def static createUserAndLogin(String email, String password) {
        return createUserAndLogin(email, password, null)
    }

    def static createUserAndLogin(String email, String password, String publicKey) {
        createUser(email, password, publicKey);

        def loginResponse = login(email, password)
        return retriveToken(loginResponse)
    }

    def static retriveToken(JsonResponseDTO jsonResponseDTO) {
        jsonResponseDTO.jsonObject.access_token as String
    }

    def static JsonResponseDTO login(String email, String password) {
        def target = JaxRsHelper.createClient().target(Configuration.getHost() + "/api/login")
        def response = target.request()
                .header("Authorization", "Basic " + new String(Base64.getEncoder().encode(("openpkw:secret").getBytes())))
                .post(Entity.form(createLoginForm(email, password)), Response.class)

        JaxRsHelper.getResponseContent(response)
    }

    def static logout(String token) {
        WebTarget target = ClientBuilder.newClient().target(Configuration.getHost() + "/api/logout");
        Response response = target.request()
                .header("Authorization", "Bearer " + token)  //add security token
                .delete();
        JaxRsHelper.getResponseContent(response)
    }

    def static Form createLoginForm(String email, String password) {
        def form = new Form()
        form.param("username", email)
        form.param("password", password)
        form.param("client_id", "openpkw")
        form.param("grant_type", "password")
        form.param("client_secret", "secret")

        form
    }

}
