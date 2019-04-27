package com.ers.models;

/**
 * Principle class used in conjunction with JWT creation and processing.
 * 
 * @author Jose Rivera
 *
 */
public class Principal {

	private String id;
	private String password;
	private String role;

	/**
	 * No Args Constructor
	 */
	public Principal() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Principle [id=" + id + ", password=" + password + ", role=" + role + "]";
	}
}
