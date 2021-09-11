package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class User {

	@Id
	private String userName;
	private String userPass;

	private String firstName;
	private String lastName;

	public User(String userName, String userPass, String firstName, String lastName) {
		this.userName = userName;
		this.userPass = userPass;

		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {

		String st = "userName: " + this.userName + "\nuserPass: " + this.userPass + "\nfirstName: " + this.firstName
				+ "\nlastName: " + this.lastName;
		return st;

	}

}
