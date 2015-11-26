package org.openpkw.web;

import java.io.Serializable;
import org.openpkw.model.entity.UserType;
import org.springframework.util.StringUtils;

/**
 *
 * @author Waldemar
 */
public class UserRegister implements Serializable{
	//String clientPublicKey;
	String devid;
	String email;
	String first_name;
	String last_name;
	String password;
	UserType userType;
/*
	public String getClientPublicKey() {
		return clientPublicKey;
	}

	public void setClientPublicKey(String clientPublicKey) {
		this.clientPublicKey = clientPublicKey;
	}
*/
	public String getDevid() {
		return devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(email) || StringUtils.isEmpty(last_name) || StringUtils.isEmpty(first_name)||
										StringUtils.isEmpty(password);
	}
	
}
