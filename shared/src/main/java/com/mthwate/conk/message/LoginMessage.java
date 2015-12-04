package com.mthwate.conk.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class LoginMessage extends AbstractMessage {

	private String username;

	public LoginMessage() {}

	public LoginMessage(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
