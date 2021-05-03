/**
 * 
 */
package it.polito.ezshop.model;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class User implements it.polito.ezshop.data.User, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6356429832423358223L;
	
	private Integer id;
	private String username, password, role;
	
	
	

	public User(Integer id, String username, String password, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
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
	
	public boolean isAdmin() {
		return this.role.compareTo("Administrator")==0 ? true : false;
	}
	

	
	
}
