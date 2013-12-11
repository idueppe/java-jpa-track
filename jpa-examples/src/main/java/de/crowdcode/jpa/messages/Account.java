package de.crowdcode.jpa.messages;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	
	private String username;
	
	private String email;

	public Account() {}
	
	public Account(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
