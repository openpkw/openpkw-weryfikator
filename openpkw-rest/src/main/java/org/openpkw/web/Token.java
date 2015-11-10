package org.openpkw.web;

import java.util.Base64;

/**
 *
 * @author Waldemar
 */
public class Token {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setToken(byte[] bToken) {
		token = Base64.getEncoder().encodeToString(bToken);
	}

	public void error() {
		token = "ERROR";
	}

}
